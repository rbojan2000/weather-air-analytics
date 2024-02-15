package aqicn.producer

import aqicn.avro.message.City
import aqicn.config.Configuration
import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

case class CitiesProducer()(implicit cities: Seq[City]) extends Producer
  with LazyLogging {

  val producer = new KafkaProducer[String, City](super.props())

  override def produce(): Unit = cities.foreach(city => sendCityMessage(city))

  def sendCityMessage(city: City): Unit = {
    producer.send(
      new ProducerRecord(
        Configuration.citiesTopic,
        city.name,
        city
      )
    )

    logger.info(s"Produced message: (${city.name}, ${city})")
  }
}

