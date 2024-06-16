import logging
import os

import pandas as pd

from server.paths import DATA_PATH

logger = logging.getLogger()


def save_data(data: pd.DataFrame, filename: str) -> None:
    if not os.path.exists(DATA_PATH):
        logger.info("Creating data directory.")
        os.mkdir(DATA_PATH)

    path = f"{DATA_PATH}/{filename}"
    logger.info(f"Writing a batch of data to path {path}.")
    data.to_csv(path)
