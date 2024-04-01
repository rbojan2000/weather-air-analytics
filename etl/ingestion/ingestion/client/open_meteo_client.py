import logging
import os
import time
import pandas as pd

from ingestion.config import Config
from ingestion.fetcher.air_quality_fetcher import AirQualityFetcher
from ingestion.fetcher.meteo_fetcher import MeteoFetcher
from ingestion.paths import HISTORICAL_WEATHER_PATH, HISTORICAL_AIR_QUALITY_PATH, CITIES_CSV_PATH, SETTINGS_PATH
from dynaconf import Dynaconf


class OpenMeteoClient:
    def __init__(self):
        self.cities: pd.DataFrame = pd.read_csv(CITIES_CSV_PATH)
        self.config: Dynaconf = Config.load(SETTINGS_PATH)

    def start_fetching_meteo_data(self):
        meteo_fetcher: MeteoFetcher = MeteoFetcher()

        for _, city in self.cities.iterrows():
            meteo_csv_file_path = os.path.join(
                HISTORICAL_WEATHER_PATH,
                f"{city["city"]}_{self.config.meteo.start_date}_{self.config.meteo.end_date}.csv"
            )

            if not os.path.exists(meteo_csv_file_path):
                result_dataframe = meteo_fetcher.get_data(latitude=city["lat"],
                                                          longitude=city["lng"],
                                                          start_date=self.config.meteo.start_date,
                                                          timezone=self.config.default.timezone,
                                                          end_date=self.config.meteo.end_date,
                                                          params=self.config.meteo.daily_params,
                                                          api=self.config.meteo.api
                                                          )
                result_dataframe.to_csv(meteo_csv_file_path)
                logging.info(f"Writing {meteo_csv_file_path}")
                time.sleep(self.config.default.sleep_time)

            else:
                logging.info(f"File {meteo_csv_file_path} already exists. Skipping API request for {city}.")

    def start_fetching_air_quality_data(self):
        air_quality_fetcher: AirQualityFetcher = AirQualityFetcher()

        for _, city in self.cities.iterrows():
            air_quality_csv_file_path = os.path.join(
                HISTORICAL_AIR_QUALITY_PATH,
                f"{city["city"]}_{self.config.air.start_date}_{self.config.air.end_date}.csv"
            )

            if not os.path.exists(air_quality_csv_file_path):
                result_dataframe = air_quality_fetcher.get_data(latitude=city["lat"],
                                                                longitude=city["lng"],
                                                                start_date=self.config.air.start_date,
                                                                timezone=self.config.default.timezone,
                                                                end_date=self.config.air.end_date,
                                                                params=self.config.air.hourly_params,
                                                                api=self.config.air.api
                                                                )
                result_dataframe.to_csv(air_quality_csv_file_path)
                logging.info(f"Writing {air_quality_csv_file_path}")
                time.sleep(self.config.default.sleep_time)

            else:
                logging.info(f"File {air_quality_csv_file_path} already exists. Skipping API request for {city}.")
