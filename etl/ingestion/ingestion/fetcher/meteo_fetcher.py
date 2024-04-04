from ingestion.fetcher.fetcher import Fetcher
import openmeteo_requests
import pandas as pd
import requests_cache
from retry_requests import retry


class MeteoFetcher(Fetcher):
    def __init__(self):
        self._openmeteo = openmeteo_requests.Client(session=self._retry_session())

    def get_data(self, latitude: str, longitude: str, params: list, start_date: str,
                 end_date: str, timezone: str, api: str) -> pd.DataFrame:
        params = {
            "latitude": latitude,
            "longitude": longitude,
            "start_date": start_date,
            "end_date": end_date,
            "daily": params,
            "timezone": "auto"
        }
        responses = self._openmeteo.weather_api(url=api, params=params)

        response = responses[0]

        daily = response.Daily()
        daily_weather_code = daily.Variables(0).ValuesAsNumpy()
        daily_temperature_2m_max = daily.Variables(1).ValuesAsNumpy()
        daily_temperature_2m_min = daily.Variables(2).ValuesAsNumpy()
        daily_temperature_2m_mean = daily.Variables(3).ValuesAsNumpy()
        daily_apparent_temperature_max = daily.Variables(4).ValuesAsNumpy()
        daily_apparent_temperature_min = daily.Variables(5).ValuesAsNumpy()
        daily_apparent_temperature_mean = daily.Variables(6).ValuesAsNumpy()
        daily_sunrise = daily.Variables(7).ValuesAsNumpy()
        daily_sunset = daily.Variables(8).ValuesAsNumpy()
        daily_daylight_duration = daily.Variables(9).ValuesAsNumpy()
        daily_sunshine_duration = daily.Variables(10).ValuesAsNumpy()
        daily_precipitation_sum = daily.Variables(11).ValuesAsNumpy()
        daily_rain_sum = daily.Variables(12).ValuesAsNumpy()
        daily_snowfall_sum = daily.Variables(13).ValuesAsNumpy()
        daily_precipitation_hours = daily.Variables(14).ValuesAsNumpy()
        daily_wind_speed_10m_max = daily.Variables(15).ValuesAsNumpy()
        daily_wind_gusts_10m_max = daily.Variables(16).ValuesAsNumpy()
        daily_wind_direction_10m_dominant = daily.Variables(17).ValuesAsNumpy()
        daily_shortwave_radiation_sum = daily.Variables(18).ValuesAsNumpy()

        daily_data = {"date": pd.date_range(
            start=pd.to_datetime(daily.Time(), unit="s", utc=True),
            end=pd.to_datetime(daily.TimeEnd(), unit="s", utc=True),
            freq=pd.Timedelta(seconds=daily.Interval()),
            inclusive="left"
        )}

        daily_data["weather_code"] = daily_weather_code
        daily_data["temperature_2m_max"] = daily_temperature_2m_max
        daily_data["temperature_2m_min"] = daily_temperature_2m_min
        daily_data["temperature_2m_mean"] = daily_temperature_2m_mean
        daily_data["apparent_temperature_max"] = daily_apparent_temperature_max
        daily_data["apparent_temperature_min"] = daily_apparent_temperature_min
        daily_data["apparent_temperature_mean"] = daily_apparent_temperature_mean
        daily_data["sunrise"] = daily_sunrise
        daily_data["sunset"] = daily_sunset
        daily_data["daylight_duration"] = daily_daylight_duration
        daily_data["sunshine_duration"] = daily_sunshine_duration
        daily_data["precipitation_sum"] = daily_precipitation_sum
        daily_data["rain_sum"] = daily_rain_sum
        daily_data["snowfall_sum"] = daily_snowfall_sum
        daily_data["precipitation_hours"] = daily_precipitation_hours
        daily_data["wind_speed_10m_max"] = daily_wind_speed_10m_max
        daily_data["wind_gusts_10m_max"] = daily_wind_gusts_10m_max
        daily_data["wind_direction_10m_dominant"] = daily_wind_direction_10m_dominant
        daily_data["shortwave_radiation_sum"] = daily_shortwave_radiation_sum

        daily_dataframe = pd.DataFrame(data=daily_data)

        return daily_dataframe

    def _retry_session(self):
        cache_session = requests_cache.CachedSession('.cache', expire_after=-1)
        return retry(cache_session, retries=5, backoff_factor=0.2)
