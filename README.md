# Weather Air Analytics

**Weather Air Analytics** project is a comprehensive platform designed for in-depth analysis of air quality data. It incorporates various modules dedicated to different aspects of air quality analysis, including real-time data processing, batch data processing, and historical data retrieval.

## Real-time Data Analysis
[streams-insights](streams-insights/README.md) focuses on analyzing real-time air quality data streams, calculating metrics, and monitoring city air pollution levels.


## Batch Data Analysis
[etl](etl/README.md) consists of:
 - [Ingestion](etl/ingestion): Responsible for historical air quality and weather forecast ingestion data.
 - [Transformation](etl/transformation/): Performs cleaning and transforming raw data into delta tables.
 - [Load](etl/load):Performs analysis and metric calculation over air quality and weather data.

## Orchestrator
Orchestrator manages the scheduling and coordination of Spark jobs within the project. For detailed information on how Spark jobs are organized and planned, refer to the [orchestrator/README.md](orchestrator/README.md)

## Visualization
Display air quality data and metrics calculated in real time. It also presents metrics and analysis from the gold Delta LakeHouse.

## Architecture
Architecture diagram illustrates the overall structure of the **Weather Air Analytics** project. It shows how different modules interact with each other to facilitate data processing and analysis.
![architecture](/docs/architecture-diagram.svg) 


Each part of the project has its own README providing detailed information and instructions specific to its functionality and usage. Please refer to these individual README files for a deeper understanding of each module within the **Weather Air Analytics** project.
