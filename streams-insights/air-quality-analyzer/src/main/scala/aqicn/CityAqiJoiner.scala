package aqicn

import aqicn.domain.City
import aqicn.domain.AirQualityValue

trait CityAqiJoiner {
  def joinCityWithAqi: (City, AirQualityValue) => CityAqiInfo = {
    (city: City, aqi: AirQualityValue) => {
      CityAqiInfo(
        url = "your_placeholder_url"
      )
    }
  }
}
