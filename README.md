# Weather Air Analytics

Comprehensive platform designed for in-depth analysis of both air quality and weather data. It comprises various modules dedicated to different aspects of data analysis, including real-time processing, batch processing, and historical data retrieval.

<br>

## Architecture
- <i> Diagram 1.1 </i> illustrates the overall structure of the project. It shows how different modules interact with each other to facilitate data processing and analysis.

<!-- ![architecture](/docs/architecture-diagram.png) -->
[![](/docs/architecture-diagram.png)](/docs/architecture-diagram.png)
<p align="center"> <i>Diagram 1.1</i></p>

<br>


## Real-time Data Analysis
- [streams-insights](streams-insights/README.md) focuses on analyzing real-time air quality data streams, calculating metrics and tracking city air pollution levels.

<br>

## Batch Data Analysis
* [etl](etl/README.md) consists of:
    1. [Ingestion](etl/ingestion): Responsible for historical air quality and weather forecast ingestion data.
    2. [Transformation](etl/transformation/): Performs cleaning and transforming raw data into Delta Tables.
    3. [Load](etl/load): Performs analysis and metric calculation over air quality and weather data.

<br>

## Orchestrator
- Manages the scheduling and coordination of etl jobs within the project.

<br>

## Visualization
- Displays real-time air quality data and dynamically calculated metrics. It shows comprehensive analysis and metrics derived from the Gold Data Lakehouse layer as well.

<br>

> [!NOTE]
> Each module of the project has its own README providing detailed information and instructions specific to its functionality and usage. Please refer to these individual README files for a deeper understanding of each module within the **Weather Air Analytics** project.
