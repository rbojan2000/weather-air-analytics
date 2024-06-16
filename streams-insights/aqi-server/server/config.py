from dynaconf import Dynaconf

from server.paths import SETTINGS_PATH

settings = Dynaconf(
    envvar_prefix="AQI_SERVER", settings_files=[SETTINGS_PATH], environments=False
)
