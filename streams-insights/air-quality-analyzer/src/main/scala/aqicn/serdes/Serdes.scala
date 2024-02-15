package aqicn.serdes

import aqicn.domain.CityAqiInfo
import aqicn.domain.CountryAirQualityMetrics
import com.sksamuel.avro4s.BinaryFormat
import com.sksamuel.avro4s.kafka.GenericSerde

trait Serdes {

  implicit val cityAqiInfoSerde: GenericSerde[CityAqiInfo] = new GenericSerde[CityAqiInfo](BinaryFormat)
  implicit val countryAqiInfoKpiSerde: GenericSerde[CountryAirQualityMetrics] = new GenericSerde[CountryAirQualityMetrics](BinaryFormat)

}
