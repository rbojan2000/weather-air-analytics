from pathlib import Path

ROOT_PATH = Path(__file__).absolute().parent.parent

DATA_PATH = ROOT_PATH.parent.parent / "data"

BRONZE_PATH = DATA_PATH / "bronze"

SETTINGS_PATH = ROOT_PATH / "ingestion" / "settings.toml"

CITIES_CSV_PATH = DATA_PATH / "european_cities.csv"

HISTORICAL_WEATHER_PATH = BRONZE_PATH / "historical_weather_daily"

HISTORICAL_AIR_QUALITY_PATH = BRONZE_PATH / "historical_air_quality"
