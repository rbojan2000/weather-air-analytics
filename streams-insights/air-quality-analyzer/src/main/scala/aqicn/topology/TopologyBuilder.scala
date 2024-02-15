package aqicn.topology

import aqicn.avro.message.{AirQuality, City}
import aqicn.config.Configuration
import aqicn.serdes.Serdes
import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.streams.Topology
import org.apache.kafka.streams.kstream.{Named, SessionWindows}
import org.apache.kafka.streams.scala.ImplicitConversions._
import org.apache.kafka.streams.scala.StreamsBuilder
import org.apache.kafka.streams.scala.kstream._
import org.apache.kafka.streams.scala.serialization.Serdes._
import org.apache.kafka.streams.state.Stores
import org.apache.kafka.streams.kstream.{GlobalKTable, Named, SessionWindows, Windowed}

case class TopologyBuilder() extends Serdes
  with LazyLogging {

  val builder: StreamsBuilder = new StreamsBuilder()

  def build: Topology = {
    val aqicnStream: KStream[String, AirQuality] = builder.stream[String, AirQuality](Configuration.airQualityTopic)
    val citiesGlobalTable: KStream[String, City] = builder.stream[String, City](Configuration.citiesTopic)


    builder.build()
  }
}
