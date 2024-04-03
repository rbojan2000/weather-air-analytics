# ingestion

Ingestion is responsible for retrieving historical air quality and weather forecast data from the [open-meteo.com](https://open-meteo.com/) APIs. This data is then stored in the bronze Data Lakehouse layer.

Below, you'll find details about the raw data schemas and column descriptions.


## Data

### Historical Hourly Air Quality

| Variable               | Valid time | Unit        | Description                                                                                                                                                                     |
|------------------------|------------|-------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| pm10                   | Instant    | μg/m³       | Particulate matter with diameter smaller than 10 µm (PM10) close to surface (10 meter above ground)                                                                           |
| pm2_5                  | Instant    | μg/m³       | Particulate matter with diameter smaller than 2.5 µm (PM2.5) close to surface (10 meter above ground)                                                                         |
| carbon_monoxide        | Instant    | μg/m³       | Carbon Monoxide concentration                                                                                                                                                  |
| nitrogen_dioxide       | Instant    | μg/m³       | Nitrogen Dioxide concentration                                                                                                                                                 |
| sulphur_dioxide        | Instant    | μg/m³       | Sulphur Dioxide concentration                                                                                                                                                  |
| ozone                  | Instant    | μg/m³       | Atmospheric gases close to surface (10 meter above ground)                                                                                                                    |
| ammonia                | Instant    | μg/m³       | Ammonia concentration. Only available for Europe.                                                                                                                              |
| aerosol_optical_depth  | Instant    | Dimensionless | Aerosol optical depth at 550 nm of the entire atmosphere to indicate haze.                                                                                                     |
| dust                   | Instant    | μg/m³       | Saharan dust particles close to surface level (10 meter above ground).                                                                                                         |
| uv_index               | Instant    | Index       | UV index considering clouds and clear sky. See ECMWF UV Index recommendation for more information                                                                             |
| uv_index_clear_sky     | Instant    | Index       | UV index considering clouds and clear sky. See ECMWF UV Index recommendation for more information                                                                             |
| alder_pollen           | Instant    | Grains/m³   | Pollen for alder plants. Only available in Europe as provided by CAMS European Air Quality forecast.                                                                           |
| birch_pollen           | Instant    | Grains/m³   | Pollen for birch plants. Only available in Europe as provided by CAMS European Air Quality forecast.                                                                           |
| grass_pollen           | Instant    | Grains/m³   | Pollen for grass plants. Only available in Europe as provided by CAMS European Air Quality forecast.                                                                           |
| mugwort_pollen         | Instant    | Grains/m³   | Pollen for mugwort plants. Only available in Europe as provided by CAMS European Air Quality forecast.                                                                         |
| olive_pollen           | Instant    | Grains/m³   | Pollen for olive plants. Only available in Europe as provided by CAMS European Air Quality forecast.                                                                           |
| ragweed_pollen         | Instant    | Grains/m³   | Pollen for ragweed plants. Only available in Europe as provided by CAMS European Air Quality forecast.                                                                         |
| european_aqi           | Instant    | European AQI  | European Air Quality Index (AQI) calculated for different particulate matter and gases individually. The consolidated european_aqi returns the maximum of all individual indices. Ranges from 0-20 (good), 20-40 (fair), 40-60 (moderate), 60-80 (poor), 80-100 (very poor) and exceeds 100 for extremely poor conditions. |
| us_aqi                 | Instant    | U.S. AQI    | United States Air Quality Index (AQI) calculated for different particulate matter and gases individually. The consolidated us_aqi returns the maximum of all individual indices. Ranges from 0-50 (good), 51-100 (moderate), 101-150 (unhealthy for sensitive groups), 151-200 (unhealthy), 201-300 (very unhealthy) and 301-500 (hazardous). |



### Daily Weather Forecast

| Variable                     | Unit       | Description                                                                                                                                                                      |
|------------------------------|------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| temperature_2m_max           | °C (°F)    | Maximum daily air temperature at 2 meters above ground                                                                                                                           |
| temperature_2m_min           | °C (°F)    | Minimum daily air temperature at 2 meters above ground                                                                                                                           |
| apparent_temperature_max     | °C (°F)    | Maximum daily apparent temperature                                                                                                                                               |
| apparent_temperature_min     | °C (°F)    | Minimum daily apparent temperature                                                                                                                                               |
| precipitation_sum            | mm         | Sum of daily precipitation (including rain, showers, and snowfall)                                                                                                               |
| rain_sum                     | mm         | Sum of daily rain                                                                                                                                                                |
| showers_sum                  | mm         | Sum of daily showers                                                                                                                                                             |
| snowfall_sum                 | cm         | Sum of daily snowfall                                                                                                                                                            |
| precipitation_hours          | hours      | Number of hours with rain                                                                                                                                                        |
| precipitation_probability_max| %          | Maximum probability of precipitation                                                                                                                                              |
| precipitation_probability_min| %          | Minimum probability of precipitation                                                                                                                                              |
| precipitation_probability_mean| %          | Mean probability of precipitation                                                                                                                                                 |
| weather_code                 | WMO code   | The most severe weather condition on a given day                                                                                                                                  |
| sunrise                      | iso8601    | Sunrise time                                                                                                                                                                     |
| sunset                       | iso8601    | Sunset time                                                                                                                                                                      |
| sunshine_duration            | seconds    | Number of seconds of sunshine per day                                                                                                                                            |
| daylight_duration            | seconds    | Number of seconds of daylight per day                                                                                                                                            |
| wind_speed_10m_max          | km/h (mph, m/s, knots) | Maximum wind speed at 10 meters above ground                                                                                                                               |
| wind_gusts_10m_max          | km/h (mph, m/s, knots) | Maximum wind gusts at 10 meters above ground                                                                                                                               |
| wind_direction_10m_dominant | °          | Dominant wind direction at 10 meters above ground                                                                                                                                 |
| shortwave_radiation_sum      | MJ/m²      | Sum of solar radiation on a given day in Megajoules                                                                                                                               |
| et0_fao_evapotranspiration  | mm         | Daily sum of ET₀ Reference Evapotranspiration of a well-watered grass field                                                                                                       |
| uv_index_max                 | Index      | Daily maximum UV Index                                                                                                                                                           |
| uv_index_clear_sky_max       | Index      | Daily maximum UV Index under clear sky conditio