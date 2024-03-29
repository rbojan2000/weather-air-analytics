from pathlib import Path

ROOT_PATH = Path(__file__).absolute().parent.parent

SETTINGS_PATH = ROOT_PATH / "server" / "settings.toml"

SCHEMAS_PATH = ROOT_PATH / "schemas"

DATA_PATH = ROOT_PATH / "data"
