package aqicn.serdes

import aqicn.avro.message.{AirQuality, City, Iaqi}
import aqicn.utils.AvroSupport
import org.apache.kafka.common.serialization.Serde

trait Serdes extends AvroSupport {
  implicit val airQualitySerde: Serde[AirQuality] = avroSerde()
  implicit val citySerde: Serde[City] = avroSerde()
  implicit val iaqiSerde: Serde[Iaqi] = avroSerde()
}
