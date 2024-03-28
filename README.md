# Weather Air Analytics

**Weather Air Analytics** project is a comprehensive platform designed for in-depth analysis of air quality data. It incorporates various modules dedicated to different aspects of air quality analysis, including real-time data processing, batch data processing, and historical data retrieval


## Historical Data Retrieval

[open-meteo](open-meteo/README.md) is responsible for retrieving historical air quality and weather forecast data.

## Real-time Data Analysis

[streams-insights](streams-insights/README.md) focuses on analyzing real-time air quality data streams, calculating metrics, and monitoring city air pollution levels.

## Batch Data Analysis

[air-meteo-insights](air-meteo-insights/README.md) performs batch data processing and conducts in-depth analysis of weather and air quality. It is instrumental in identifying historical air quality trends and patterns.


Each part of the project has its own README providing detailed information and instructions specific to its functionality and usage. Please refer to these individual README files for a deeper understanding of each module within the **Weather Air Analytics** project.

## Orchestrator
Orchestrator manages the scheduling and coordination of Spark jobs within the project. For detailed information on how Spark jobs are organized and planned, refer to the [orchestrator/README.md](orchestrator/README.md)

## Architecture
Architecture diagram illustrates the overall structure of the **Weather Air Analytics** project. It shows how different modules interact with each other to facilitate data processing and analysis.

![architecture](/docs/architecture.png)
