package aqicn.producer

import aqicn.api.Api
import aqicn.config.Configuration
import aqicn.domain._
import aqicn.utils.CsvReader

object Engine {
  private val cities = CsvReader.readCities(Configuration.europeanCitiesPath)

  def carEngine(api: Api): Seq[(AirQualityId, AirQualityValue)] =
    for {
      city <- cities
      aqi = api.getAirQualityByCity(city)
    } yield AirQualityId(city.name) -> aqi

}
