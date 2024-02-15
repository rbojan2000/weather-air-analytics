package aqicn.config

import com.typesafe.config.ConfigFactory

object Configuration {

  private val config = ConfigFactory.load("application.conf")

  val schemaRegistryUrl: String = config.getString("kafka.schema-registry-url")
  val bootstrapServers: String = config.getString("kafka.bootstrap-servers")
  val acks: String = config.getString("kafka.producer.acks")
  val sleepTime: Int = config.getInt("kafka.producer.sleep-time-ms")

  val aqicnRoot: String = config.getString("aqicn.api.root")
  val aqicnApiKey: String = config.getString("aqicn.api.key")

  val keySerializer: String = config.getString("kafka.key-serializer")
  val valueSerializer: String = config.getString("kafka.value-serializer")

  val airQualityTopic: String = config.getString("kafka.topics.air-quality")
  val citiesTopic: String = config.getString("kafka.topics.cities")

  val europeanCitiesPath: String = config.getString("csv.cities")

}
