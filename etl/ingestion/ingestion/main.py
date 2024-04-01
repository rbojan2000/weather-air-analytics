import argparse
import logging
from ingestion.client.open_meteo_client import OpenMeteoClient

logging.basicConfig(level=logging.INFO)


def run():
    parser = argparse.ArgumentParser(
        description='Fetch historical weather or air quality data.'
    )
    parser.add_argument('--data-type',
                        choices=['meteo', 'air'],
                        required=True,
                        help='Specify the type of data to fetch (weather or air)'
                        )
    args = parser.parse_args()
    open_meteo_client: OpenMeteoClient = OpenMeteoClient()

    if args.data_type == 'meteo':
        open_meteo_client.start_fetching_meteo_data()

    elif args.data_type == 'air':
        open_meteo_client.start_fetching_air_quality_data()


if __name__ == '__main__':
    run()
