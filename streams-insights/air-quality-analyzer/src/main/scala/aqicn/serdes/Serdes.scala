package aqicn.serdes

import aqicn.avro.message.{AirQuality, AirQualityWithPollutionLevel, City, CityAqiInfo, CountryAirQualityMetrics, Iaqi}
import aqicn.utils.AvroSupport
import org.apache.kafka.common.serialization.Serde

trait Serdes extends AvroSupport {

  implicit val airQualitySerde: Serde[AirQuality] = avroSerde()
  implicit val citySerde: Serde[City] = avroSerde()
  implicit val iaqiSerde: Serde[Iaqi] = avroSerde()
  implicit val cityAqiInfoSerde: Serde[CityAqiInfo] = avroSerde()
  implicit val airQualityWithPollutionLevelSerde: Serde[AirQualityWithPollutionLevel] = avroSerde()
  implicit val countryAirQualityMetricsSerde: Serde[CountryAirQualityMetrics] = avroSerde()

}
