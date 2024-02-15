package aqicn.config

import com.typesafe.config.ConfigFactory

object Configuration {

  private val config = ConfigFactory.load("application.conf")

  val schemaRegistryUrl: String = config.getString("kafka.schema-registry-url")
  val bootstrapServers: String = config.getString("kafka.bootstrap-servers")

  val airQualityTopic: String = config.getString("kafka.topics.air-quality")
  val citiesTopic: String = config.getString("kafka.topics.cities")

  val appId: String = config.getString("air-quality-analyzer.app-id")

}
