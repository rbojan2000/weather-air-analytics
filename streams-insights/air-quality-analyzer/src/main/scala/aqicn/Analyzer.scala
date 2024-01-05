package aqicn

import aqicn.AvroSerdes._
import aqicn.avro._
import aqicn.config.Configuration
import aqicn.domain._
import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsConfig.{APPLICATION_ID_CONFIG, BOOTSTRAP_SERVERS_CONFIG}
import org.apache.kafka.streams.scala.ImplicitConversions._
import org.apache.kafka.streams.scala._
import org.apache.kafka.streams.scala.kstream.KStream

import java.util.Properties

object Analyzer extends App with LazyLogging {

  val props = new Properties()
  props.put(APPLICATION_ID_CONFIG, Configuration.kafkaAirQualityAnalyzerAppID)
  props.put(BOOTSTRAP_SERVERS_CONFIG, Configuration.kafkaBootstrapServers)

  val builder: StreamsBuilder = new StreamsBuilder

  val aqicnData: KStream[AirQualityId, AirQualityValue] = builder.stream[AirQualityId, AirQualityValue](Configuration.kafkaAirQualityTopic).peek((K, V) => logger.info("val: " + V + "key " + K + "\n"))
  val citiesData: KStream[CityId, City] = builder.stream[CityId, City](Configuration.kafkaCitiesTopic).peek((K, V) => logger.info("val: " + V + "key " + K + "\n"))


  val topology = builder.build()
  val streams = new KafkaStreams(topology, props)

  streams.start()

  Runtime.getRuntime.addShutdownHook(new Thread(() => streams.close()))
}