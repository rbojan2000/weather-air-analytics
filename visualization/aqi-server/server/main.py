import logging
import sys

from server.exception import KafkaMessageFetchException, UnsupportedDeserializerException
from server.kafka.configuration import Configuration
from server.config import settings
from server.kafka.consumer import KafkaConsumer

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)


def start() -> None:
    consumer_configuration = Configuration(
        settings.kafka.bootstrap_servers,
        settings.kafka.group_id,
        settings.kafka.auto_offset_reset,
        settings.kafka.enable_auto_commit,
        settings.kafka.max_poll_records,
        settings.kafka.schema_registry_url,
        settings.kafka.batch_size,
    )
    consumer = KafkaConsumer(consumer_configuration)

    try:
        consumer.subscribe(settings.kafka.topics)
    except UnsupportedDeserializerException as error:
        logger.error(error)
        logger.info("Exiting application.")
        sys.exit(-1)

    logger.info("Starting with message fetch.")
    try:
        consumer.poll(consumer_configuration.max_poll_records, 1.0)
    except KafkaMessageFetchException as error:
        logger.error(error)
        logger.info("Exiting application.")
        sys.exit(-1)


if __name__ == "__main__":
    start()
