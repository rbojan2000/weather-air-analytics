package config

import com.typesafe.config.ConfigFactory

object AppConfig {
  private val config = ConfigFactory.load("application.conf")

  val csvBasePath: String = config.getString("csv.base_path")
  val deltaAirQuality: String = config.getString("delta.air_quality")
  val deltaWeather: String = config.getString("delta.weather")
  val europeanCitiesCsvPath: String = config.getString("european_cities_csv_path")
}
