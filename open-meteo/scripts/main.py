import argparse
import time
import json
import os
import logging
import pandas as pd
from historical_weather import historical_weather_daily_data_by_lat_long
from air_quality import historical_air_quality_data_by_lat_long

def fetch_weather_data(selected_city_data: pd.DataFrame, start_date: str, end_date: str, config: any, timezone: str):
    historical_weather_daily_data_path = config["historical_weather_daily_data_path"]
    weather_daily_params = config["weather_daily_params"]

    for _, row in selected_city_data.iterrows():
        latitude, longitude, city = row["lat"], row["lng"], row["city"]
        csv_file_path = os.path.join(historical_weather_daily_data_path, f"{city}_{start_date}_{end_date}.csv")
        if not os.path.exists(csv_file_path):
            result_dataframe = historical_weather_daily_data_by_lat_long(latitude, longitude, start_date, end_date, weather_daily_params, timezone)
            result_dataframe.to_csv(csv_file_path)
            logging.info(f"Writing {csv_file_path}")
            time.sleep(60)

        else:
            logging.info(f"File {csv_file_path} already exists. Skipping API request for {city}.")

def fetch_air_quality_data(selected_city_data: pd.DataFrame, start_date: str, end_date: str, config: any, timezone: str):
    historical_air_quality_data_path = config["historical_air_quality_data_path"]
    air_hourly_params = config["air_hourly_params"]

    for _, row in selected_city_data.iterrows():
        latitude, longitude, city = row["lat"], row["lng"], row["city"]
        csv_file_path = os.path.join(historical_air_quality_data_path, f"{city}_{start_date}_{end_date}.csv")

        if not os.path.exists(csv_file_path):
            start_date = start_date
            result_dataframe = historical_air_quality_data_by_lat_long(latitude, longitude, air_hourly_params, start_date, end_date, timezone)
            result_dataframe.to_csv(csv_file_path)
            logging.info(f"Writing {csv_file_path}")
            time.sleep(60)

        else:
            logging.info(f"File {csv_file_path} already exists. Skipping API request for {city}.")

def main():
    parser = argparse.ArgumentParser(description='Fetch historical weather or air quality data.')
    parser.add_argument('--data-type', choices=['weather', 'air'], required=True, help='Specify the type of data to fetch (weather or air)')
    args = parser.parse_args()

    logging.basicConfig(level=logging.INFO)

    with open("../config/config.json") as config_file:
        config = json.load(config_file)

    cities_csv_path, start_date, end_date, timezone = config["cities_csv_path"], config["start_date"], config["end_date"], config["timezone"]
    cities_data = pd.read_csv(cities_csv_path)
    selected_columns = ["lat", "lng", "city"]
    selected_city_data = cities_data[selected_columns]

    if args.data_type == 'weather':
        fetch_weather_data(selected_city_data, start_date, end_date, config, timezone)
                
    elif args.data_type == 'air':
        fetch_air_quality_data(selected_city_data, start_date, end_date, config, timezone)

if __name__ == '__main__':
    main()
