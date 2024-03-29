from typing import Any

import pandas as pd


def transform_city_aqi_data(city_aqi_data: list[dict[str, Any]]) -> pd.DataFrame:
    df = pd.DataFrame.from_records(city_aqi_data)
    iaqi_df = pd.json_normalize(df['iaqi'])
    iaqi_df.columns = [f"iaqi_{col}" for col in iaqi_df.columns]
    city_df = pd.json_normalize(df['city'])
    city_df.columns = [f"city_{col}" if col not in ["lat", "lon"] else col for col in city_df.columns]

    df = pd.concat([df, iaqi_df, city_df], axis=1)
    df.drop(columns=['iaqi', 'city'], inplace=True)

    return df
