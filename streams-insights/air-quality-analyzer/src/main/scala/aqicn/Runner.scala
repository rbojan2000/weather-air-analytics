package aqicn

import aqicn.config.Configuration
import aqicn.topology.TopologyBuilder
import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsConfig.{APPLICATION_ID_CONFIG, BOOTSTRAP_SERVERS_CONFIG}

import java.util.Properties

object Runner extends App
  with LazyLogging {

  val props = new Properties()
  props.put(BOOTSTRAP_SERVERS_CONFIG, Configuration.bootstrapServers)
  props.put(APPLICATION_ID_CONFIG, Configuration.appId)

  val topology = TopologyBuilder.apply().build
  logger.info(topology.describe().toString)

  val app = new KafkaStreams(topology, props)
  app.start()

  sys.addShutdownHook {
    logger.info("Shutting down Stream app...")
    app.close()
  }
}
