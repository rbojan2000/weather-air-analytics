class AqiServerException(Exception):
    """Base exception class for aqi server application."""


class KafkaMessageFetchException(AqiServerException):
    """Raises when there is error while fetching messages."""


class UnsupportedDeserializerException(AqiServerException):
    """Raises when consumers subscribe to topic that has no defined schema."""


class SchemaNotExistsException(AqiServerException):
    """Raises when there is unsupported message type on the input."""
