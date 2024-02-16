# streams-insights
The streaming part of the project consists of a producer and an analyzer.

## Kafka Infrastructure and Topics
This project utilizes Kafka for streaming data processing. The infrastructure is set up using Docker Compose, comprising `Kafka broker`, `topic setup`, and a `Schema Registry`.

Initialize Kafka cluster with
```bash
docker compose up -d
```

## Kafka Topics
- `aqicn.air.quality`
- `aqicn.cities`
- `aqicn.city.air.pollutant`
- `aqicn.metrics.country`

## Producer

Please refer to the [README](producer/README.md) file in the producer directory for details about the producer.

## Analyzer

Please refer to the [README](air-quality-analyzer/README.md) file in the air-quality-analyzer directory for details about the analyzer.

