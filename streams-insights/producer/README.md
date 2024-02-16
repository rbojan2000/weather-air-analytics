# Producer
This application serves the dual purpose of generating city data and air quality data, contributing to the `aqicn.cities` and `aqicn.air.quality` Kafka topics.

## Requirements
- sbt 1.9.8
- Scala 2.13.11

## Running

### Producing city data
To produce cities data, use the following command:

```bash
sbt "run -d cities"
```

This command populates information regarding cities to the `aqicn.cities` Kafka topic.


#### Columns:
| Field        | Description                                      |
|--------------|--------------------------------------------------|
| id           | Unique identifier of the city                    |
| city         | Name of the city                                 |
| lat          | Latitude of the city                             |
| lng          | Longitude of the city                            |
| country      | Country where the city is located                |
| population   | Population of the city                           |

### Producing air quality data
To generate air quality data, execute:

```bash
sbt "run -d air-quality"
```
This command retrieves information about air quality from `https://aqicn.org` for European cities with over 50,000 inhabitants, and it publishes this data to the `aqicn.air.quality` Kafka topic.


#### Columns:
| Field           | Description                                               |
|-----------------|-----------------------------------------------------------|
| url             | URL of the source                                         |
| stationName     | Name of the monitoring station                            |
| dominantPol     | Dominant pollutant                                        |
| aqi             | Air Quality Index (AQI), nullable (default: null)         |
| index           | Index                                                     |
| STM             | Short-term exposure value (nullable, default: null)       |
| iaqi            | Individual Air Quality Index (IAQI), structured as follows|
| co              | Carbon monoxide (CO)                                      |
| h               | Humidity                                                  |
| no2             | Nitrogen dioxide (NO2)                                    |
| o3              | Ozone (O3)                                                |
| p               | Atmospheric pressure (P)                                  |
| pm10            | Particulate matter 10 micrometers or less (PM10)          |
| pm25            | Particulate matter 2.5 micrometers or less (PM2.5)        |
| so2             | Sulfur dioxide (SO2)                                      |


