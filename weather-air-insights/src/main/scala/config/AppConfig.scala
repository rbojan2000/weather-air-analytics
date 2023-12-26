package config

import com.typesafe.config.ConfigFactory

object AppConfig {
  private val config = ConfigFactory.load("application.conf")

  val csvBasePath: String = config.getString("csv.base_path")
  val deltaBasePath: String = config.getString("delta.base_path")
  val europeanCitiesCsvPath: String = config.getString("european_cities_csv_path")
}
