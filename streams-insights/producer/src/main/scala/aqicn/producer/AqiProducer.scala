package aqicn.producer

import aqicn.api.Api
import aqicn.avro.message.{AirQuality, City}
import aqicn.config.Configuration
import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

case class AqiProducer(api: Api)(implicit cities: Seq[City]) extends Producer
  with LazyLogging {

  val producer = new KafkaProducer[String, AirQuality](super.props())

  override def produce(): Unit =
    while (true) {
      cities.foreach { city =>
        val airQuality = api.getAirQualityByCity(city)
        sendAqiMessage(airQuality)
      }
    }

  def sendAqiMessage(aqi: AirQuality): Unit = {
    producer.send(
      new ProducerRecord(
        Configuration.airQualityTopic,
        aqi.city.name,
        aqi
      )
    )

    logger.info(s"Produced message: ($aqi.city.name, ${aqi})")
  }
}
