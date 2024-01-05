package aqicn.config

import com.typesafe.config.ConfigFactory

object Configuration {
  private val config = ConfigFactory.load("application.conf")

  // aqicn
  val aqicnRoot: String = config.getString("aqicn.api.root")


  // kafka
  val kafkaBootstrapServers: String = config.getString("kafka.bootstrap-servers")
  val kafkaSchemaRegistryUrl: String = config.getString("kafka.schema-registry-url")

  val kafkaProducerAcks: String = config.getString("kafka.producer.acks")
  val kafkaProducerAppID: String = config.getString("kafka.producer.app-id")
  val kafkaProducerSleepTime: Int = config.getInt(  "kafka.producer.sleep-time-ms")
  val kafkaProducerCitiesParts: Int = config.getInt("kafka.producer.cities-parts")

  val kafkaAirQualityAnalyzerAppID: String = config.getString("kafka.air-quality-analyzer.app-id")

  val kafkaAirQualityTopic: String = config.getString("kafka.topics.air-quality")
  val kafkaCitiesTopic: String = config.getString("kafka.topics.cities")

  //csv
  val europeanCitiesPath: String = config.getString("csv.cities")
}
