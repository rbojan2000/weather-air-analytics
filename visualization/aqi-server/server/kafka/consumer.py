# pylint: disable=unspecified-encoding
# pylint: disable=logging-fstring-interpolation
# pylint: disable=too-many-try-statements

import logging
import os
from typing import Any

import pandas as pd
from confluent_kafka import Consumer
from confluent_kafka.error import KafkaError
from confluent_kafka.schema_registry import SchemaRegistryClient
from confluent_kafka.schema_registry.avro import AvroDeserializer
from confluent_kafka.serialization import MessageField, SerializationContext
from fastparquet import write as parquet_write

from server.exception import (
    KafkaMessageFetchException,
    SchemaNotExistsException,
    UnsupportedDeserializerException,
)
from server.kafka.configuration import Configuration
from server.paths import DATA_PATH, SCHEMAS_PATH

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
        self.parquet_batch_size = configuration.parquet_batch_size

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
                self.country_metrics_deserializer = self._init_deserializer(
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
                            data = self._process_country_metrics_message(message)
                            city_aqi.append(data)
                        # elif message.topic() == CITY_AIR_POLLUTANT_TOPIC:
                        #     data = self._process_flight_message(message)
                        #     city_aqi.append(data)
                        else:
                            logger.warning(
                                f"Unsupported message from topic {message.topic()}. Skipping."
                            )
                            continue

                        if len(city_aqi) > self.parquet_batch_size:
                            self._save_data(city_aqi, filename="city_aqi")
                            city_aqi.clear()

                logger.info("Commiting offsets for batch of messages.")
                self.consumer.commit(asynchronous=True)

            except KeyboardInterrupt:
                logger.info("Stopping message consuming. Exiting.")
                self.consumer.close()
                break

    def _process_country_metrics_message(self, message) -> dict[str, Any]:
        topic = message.topic()
        value = message.value()
        data = self.country_metrics_deserializer(
            value, SerializationContext(topic, MessageField.VALUE)
        )

        return data

    # def _process_flight_message(self, message) -> dict[str, Any]:
    #     topic = message.topic()
    #     value = message.value()
    #     data = self.flight_event_deserializer(
    #         value, SerializationContext(topic, MessageField.VALUE)
    #     )
    #
    #     return data

    def _save_data(self, data: list[dict[str, Any]], filename: str) -> None:
        if not os.path.exists(DATA_PATH):
            logger.info("Creating data directory.")
            os.mkdir(DATA_PATH)

        path = f"{DATA_PATH}/{filename}"
        df = pd.DataFrame.from_records(data)
        logger.info(f"Writing a batch of data to path {path}.")
        parquet_write(path, df, append=True, compression="SNAPPY")