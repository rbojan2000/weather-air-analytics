package aqicn.producer

import aqicn.api.Api
import aqicn.config.Configuration
import aqicn.domain._
import aqicn.utils.CsvReader
import com.typesafe.scalalogging.LazyLogging

trait Engine extends LazyLogging {
  val cities = CsvReader.readCities(Configuration.europeanCitiesPath)

  def airQualityEngine(api: Api, fromIndex: Int, toIndex: Int): Seq[(AirQualityId, AirQualityValue)] =
    for {
      city <- cities.slice(fromIndex, toIndex)
      aqi = api.getAirQualityByCity(city)
    } yield AirQualityId(city.name) -> aqi

  def calculateIndexRanges(totalCities: Int, parts: Int): Seq[(Int, Int)] = {
    val citiesPerPart = totalCities / parts
    val remainder = totalCities % parts

    (0 until parts).map { part =>
      val fromIndex = part * citiesPerPart + math.min(part, remainder)
      val toIndex = (part + 1) * citiesPerPart + math.min(part + 1, remainder) - 1
      (fromIndex, toIndex)
    }
  }

}
