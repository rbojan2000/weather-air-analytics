import openmeteo_requests

import requests_cache
import pandas as pd
from retry_requests import retry

# Setup the Open-Meteo API client with cache and retry on error
cache_session = requests_cache.CachedSession('.cache', expire_after = 3600)
retry_session = retry(cache_session, retries = 5, backoff_factor = 0.2)
openmeteo = openmeteo_requests.Client(session = retry_session)
url = "https://air-quality-api.open-meteo.com/v1/air-quality"

def historical_air_quality_data_by_lat_long(latitude: str, longitude: str, hourly_params: list, start_date: str, end_date: str, timezone: str) -> pd.DataFrame:
	params = {
		"latitude": latitude,
		"longitude": longitude,
		"hourly": hourly_params,
  		"timezone": timezone,
		"start_date": start_date,
		"end_date": end_date
	}
 #ede1ba751854e4afbbfa2b7620fc3f44629c237d
	responses = openmeteo.weather_api(url, params=params)
	response = responses[0]

	hourly = response.Hourly()
	hourly_pm10 = hourly.Variables(0).ValuesAsNumpy()
	hourly_pm2_5 = hourly.Variables(1).ValuesAsNumpy()
	hourly_carbon_monoxide = hourly.Variables(2).ValuesAsNumpy()
	hourly_nitrogen_dioxide = hourly.Variables(3).ValuesAsNumpy()
	hourly_sulphur_dioxide = hourly.Variables(4).ValuesAsNumpy()
	hourly_ozone = hourly.Variables(5).ValuesAsNumpy()
	hourly_aerosol_optical_depth = hourly.Variables(6).ValuesAsNumpy()
	hourly_dust = hourly.Variables(7).ValuesAsNumpy()
	hourly_uv_index = hourly.Variables(8).ValuesAsNumpy()
	hourly_uv_index_clear_sky = hourly.Variables(9).ValuesAsNumpy()
	hourly_ammonia = hourly.Variables(10).ValuesAsNumpy()
	hourly_alder_pollen = hourly.Variables(11).ValuesAsNumpy()
	hourly_birch_pollen = hourly.Variables(12).ValuesAsNumpy()
	hourly_grass_pollen = hourly.Variables(13).ValuesAsNumpy()
	hourly_mugwort_pollen = hourly.Variables(14).ValuesAsNumpy()
	hourly_olive_pollen = hourly.Variables(15).ValuesAsNumpy()
	hourly_ragweed_pollen = hourly.Variables(16).ValuesAsNumpy()

	hourly_data = {"date": pd.date_range(
		start = pd.to_datetime(hourly.Time(), unit = "s"),
		end = pd.to_datetime(hourly.TimeEnd(), unit = "s"),
		freq = pd.Timedelta(seconds = hourly.Interval()),
		inclusive = "left"
	)}
	hourly_data["pm10"] = hourly_pm10
	hourly_data["pm2_5"] = hourly_pm2_5
	hourly_data["carbon_monoxide"] = hourly_carbon_monoxide
	hourly_data["nitrogen_dioxide"] = hourly_nitrogen_dioxide
	hourly_data["sulphur_dioxide"] = hourly_sulphur_dioxide
	hourly_data["ozone"] = hourly_ozone
	hourly_data["aerosol_optical_depth"] = hourly_aerosol_optical_depth
	hourly_data["dust"] = hourly_dust
	hourly_data["uv_index"] = hourly_uv_index
	hourly_data["uv_index_clear_sky"] = hourly_uv_index_clear_sky
	hourly_data["ammonia"] = hourly_ammonia
	hourly_data["alder_pollen"] = hourly_alder_pollen
	hourly_data["birch_pollen"] = hourly_birch_pollen
	hourly_data["grass_pollen"] = hourly_grass_pollen
	hourly_data["mugwort_pollen"] = hourly_mugwort_pollen
	hourly_data["olive_pollen"] = hourly_olive_pollen
	hourly_data["ragweed_pollen"] = hourly_ragweed_pollen

	hourly_dataframe = pd.DataFrame(data = hourly_data)
	return hourly_dataframe
