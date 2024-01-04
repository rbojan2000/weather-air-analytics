package aqicn

package object domain {
  case class AirQualityId(city: String)

  case class Iaqi(
                   co: Double,
                   h: Double,
                   no2: Double,
                   o3: Double,
                   p: Double,
                   pm10: Double,
                   pm25: Double,
                   so2: Double,
                 )

  case class AirQualityValue(
                              url: String,
                              stationName: String,
                              dominentPol: String,
                              aqi: Int,
                              index: Int,
                              STM: Long,
                              iaqi: Iaqi,
                              city: City
                            )

  case class City(id: Int, lat: String, lon: String, name: String, country: String, population: Long)
}
