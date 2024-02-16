package aqicn.config

import com.typesafe.config.ConfigFactory

object Configuration {

  private val config = ConfigFactory.load("application.conf")

  val schemaRegistryUrl: String = config.getString("kafka.schema-registry-url")
  val bootstrapServers: String = config.getString("kafka.bootstrap-servers")

  val airQualityTopic: String = config.getString("kafka.topics.air-quality")
  val citiesTopic: String = config.getString("kafka.topics.cities")
  val cityairpollutantTopic: String = config.getString("kafka.topics.city-air-pollutant")
  val countryAqiMetricsTopic: String = config.getString("kafka.topics.country-air-quality-metrics")

  val windowDuration: Int = config.getInt("kafka.sliding-windows-duration-mins")

  val appId: String = config.getString("air-quality-analyzer.app-id")

}
