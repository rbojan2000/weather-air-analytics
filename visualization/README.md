# visualization

Module serves as a component to visualize air quality and pollution data in real-time.

## Table of contents
* [Overview](#overview)
* [Dashboard](#dashboard)
  * [Dashboard Example](#dashboard-example)
* [Infrastructure Setup](#infrastructure-setup)

## Overview
The `aqi-server` component is responsible for fetching data from `aqicn.city.air.pollutant` & `aqicn.metrics.country` Kafka topics and storing it in CSV files. It leverages the `Grafana server` to visualize the stored data effectively. Specifically, Grafana utilizes the `marcusolsson-csv-datasource` plugin as a data source to retrieve information from CSV files.

## Dashboard
Currently, the Visualization module features a single dashboard that provides real-time insights into air quality metrics. The dashboard showcases a heatmap, offering a comprehensive view of AQI (Air Quality Index) and pollution levels based on the geographic coordinates(latitude and longitude) of monitoring devices.

### Dashboard Example
![dashboard-example](/docs/city-aqi-dashboard-example.png)

## Infrastructure Setup
Start `aqi-server` kafka consumer and `grafana server` with [city-aqi-dashboard](/visualization/grafana/city-aqi-dashboard.json):

```bash
docker compose up
```