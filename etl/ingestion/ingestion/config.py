from dynaconf import Dynaconf


class Config(Dynaconf):
    @classmethod
    def load(cls, config_path: str) -> Dynaconf:
        return Dynaconf(
            settings_files=[config_path],
        )
