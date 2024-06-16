import socket
from typing import Any


class Configuration:
    """Configuration class for Aqi server as Kafka consumer."""

    def __init__(
            self,
            servers: list[str],
            group_id: str,
            offset_reset: str,
            enable_auto_commit: bool,
            max_poll_records: int,
            schema_registry_url: str,
            batch_size: int,
    ) -> None:
        self.bootstrap_servers = ",".join(servers)
        self.group_id = group_id
        self.client_id = socket.gethostname()
        self.auto_offset_reset = offset_reset
        self.enable_auto_commit = enable_auto_commit
        self.max_poll_records = max_poll_records
        self.schema_registry_url = schema_registry_url
        self.batch_size = batch_size

    def consumer_config(self) -> dict[str, Any]:
        return {
            "bootstrap.servers": self.bootstrap_servers,
            "client.id": self.client_id,
            "group.id": self.group_id,
            "auto.offset.reset": self.auto_offset_reset,
            "enable.auto.commit": self.enable_auto_commit,
        }
