package aqicn

package object domain {
  case class AirQualityId(city: String)

  case class CityId(name: String)

  case class Iaqi(
                   co: Option[Double],
                   h: Option[Double],
                   no2: Option[Double],
                   o3: Option[Double],
                   p: Option[Double],
                   pm10: Option[Double],
                   pm25: Option[Double],
                   so2: Option[Double],
                 )

  case class AirQualityValue(
                              url: String,
                              stationName: String,
                              dominentPol: String,
                              aqi: Option[Int],
                              index: Int,
                              STM: Option[Long],
                              iaqi: Iaqi,
                              city: City
                            )

  case class City(
                   id: Int,
                   lat: String,
                   lon: String,
                   name: String,
                   country: String,
                   population: Long
                 )
}
