import logging
from typing import Any

from confluent_kafka import Consumer
from confluent_kafka.error import KafkaError
from confluent_kafka.schema_registry import SchemaRegistryClient
from confluent_kafka.schema_registry.avro import AvroDeserializer
from confluent_kafka.serialization import MessageField, SerializationContext

from server.common import save_data
from server.exception import (
    KafkaMessageFetchException,
    SchemaNotExistsException,
    UnsupportedDeserializerException,
)
from server.kafka.configuration import Configuration
from server.paths import SCHEMAS_PATH
from server.transofrm import transform_city_aqi_data

logger = logging.getLogger(__name__)

# COUNTRY_METRICS_TOPIC = "aqicn.country.metrics"
CITY_AIR_POLLUTANT_TOPIC = "aqicn.city.air.pollutant"


class KafkaConsumer:
    def __init__(self, configuration: Configuration) -> None:
        logger.info("Initializing connection to schema registry.")
        self.schema_registry = SchemaRegistryClient(
            {"url": configuration.schema_registry_url}
        )

        config_dict = configuration.consumer_config()
        self.batch_size = configuration.batch_size

        logger.info("Initializing consumer.")
        self.consumer = Consumer(config_dict)

    def _init_deserializer(self, topic: str, schema_name: str) -> AvroDeserializer:
        try:
            schema_name = f"{schema_name}.avsc"
            with open(SCHEMAS_PATH / schema_name, "r") as schema_file:
                schema = schema_file.read()
                return AvroDeserializer(self.schema_registry, schema)
        except OSError as error:
            logger.error(error)
            raise SchemaNotExistsException(
                f"Schema for topic {topic} does not exist."
            ) from error

    def subscribe(self, topics: list[str]) -> None:
        """Subscribe to list of topics and initialize their message deserializers.

        Args:
            topics (list[str]): List of topics to subscribe to.

        Raises:
            UnsupportedDeserializerException: Topic doesn't have supported deserializer.
        """
        logger.info(f"Subscribing to topics: {topics}.")
        self.consumer.subscribe(topics)

        logger.info("Initializing deserializers.")

        for topic in topics:
            if topic == CITY_AIR_POLLUTANT_TOPIC:
                self.city_aqi_deserializer = self._init_deserializer(
                    CITY_AIR_POLLUTANT_TOPIC, "air_quality_with_pollution_level"
                )
            # elif topic == CITY_AIR_POLLUTANT_TOPIC:
            #     self.city_air_pollutant_deserializer = self._init_deserializer(
            #         CITY_AIR_POLLUTANT_TOPIC, "city_aqi_info"
            #     )
            else:
                raise UnsupportedDeserializerException(
                    f"Topic {topic} doesn't have supported deserializer."
                )

    def poll(self, num_messages: int, timeout: float) -> None:
        """Fetch and process batch of messages.

        Args:
            num_messages (int): Number of messages to poll from the topic(s)
            timeout (float): Timeout period on batches fetch.

        Raises:
            KafkaMessageFetchException: Error while fetching batch of messages.
        """

        city_aqi: list[dict[str, Any]] = []
        while True:
            try:
                messages = self.consumer.consume(num_messages, timeout)
                if messages is None:
                    continue

                for message in messages:
                    if message.error():
                        if message.error().code() == KafkaError._PARTITION_EOF:
                            logger.error(
                                f"Topic {message.topic()} partition {message.partition()} reached end at offset {message.offset()}."
                            )
                        else:
                            raise KafkaMessageFetchException(message.error())
                    else:
                        if message.topic() == CITY_AIR_POLLUTANT_TOPIC:
                            data = self._process_city_aqi_message(message)
                            city_aqi.append(data)
                        # elif message.topic() == CITY_AIR_POLLUTANT_TOPIC:
                        #     data = self._process_flight_message(message)
                        #     city_aqi.append(data)
                        else:
                            logger.warning(
                                f"Unsupported message from topic {message.topic()}. Skipping."
                            )
                            continue

                        if len(city_aqi) > self.batch_size:
                            transformed_city_aqi_df = transform_city_aqi_data(city_aqi)
                            save_data(transformed_city_aqi_df, filename="city_aqi.csv")
                            city_aqi.clear()

                    logger.info("Commiting offsets for batch of messages.")
                self.consumer.commit(asynchronous=True)

            except KeyboardInterrupt:
                logger.info("Stopping message consuming. Exiting.")
                self.consumer.close()
                break

    def _process_city_aqi_message(self, message) -> dict[str, Any]:
        topic = message.topic()
        value = message.value()
        data = self.city_aqi_deserializer(
            value, SerializationContext(topic, MessageField.VALUE)
        )

        return data
