package aqicn

import aqicn.config.Configuration
import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsConfig.{APPLICATION_ID_CONFIG, BOOTSTRAP_SERVERS_CONFIG}

import java.util.Properties

object Analyzer extends App with LazyLogging {

  val props = new Properties()
  props.put(APPLICATION_ID_CONFIG, Configuration.kafkaAirQualityAnalyzerAppID)
  props.put(BOOTSTRAP_SERVERS_CONFIG, Configuration.kafkaBootstrapServers)


  val topology = TopologyBuilder.apply().build

  logger.info(topology.describe().toString)
  val app = new KafkaStreams(topology, props)

  app.start()

  Runtime.getRuntime.addShutdownHook(new Thread(() => app.close()))
}
