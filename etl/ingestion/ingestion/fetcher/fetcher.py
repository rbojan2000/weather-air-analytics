from abc import ABC, abstractmethod

import pandas as pd


class Fetcher(ABC):
    @abstractmethod
    def get_data(self,
                 latitude: str,
                 longitude: str,
                 params: list,
                 start_date: str,
                 end_date: str,
                 timezone: str,
                 api: str
                 ) -> pd.DataFrame:
        pass
