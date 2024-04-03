# load

## jobs:
 - `hourly-pollutant-metrics`: This job compares the measured quantity of a particular air pollutant for the given hour against the maximum measured quantity of that pollutant for the same hour across all cities, as well as against the average quantity of that pollutant in the air for the same hour across all cities where measurements are taken.
 
 - `hourly-pollutant-cities-rank-by-max-pollutant-concetration`: This job compares the measured concentration value of a pollutant with the maximum and minimum values measured across all cities. Additionally, it determines the rank for each city based on the concentration of that pollutant at that moment.

 - `correlation-between-air-quality-and-weather`: This job calculates the degree of correlation between a parameter for air quality and a parameter from the weather forecast. The correlation coefficient is computed based on all collected historical data.

 - `wind-speed-pollutant-ratio`: This job calculates the average wind speed at a height of 10 meters above the ground in time intervals of 3 days, the average daily concentration of pollen for the same 3-day interval, and computes their relationship for that time period.
