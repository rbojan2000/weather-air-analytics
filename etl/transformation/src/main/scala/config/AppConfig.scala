package config

import com.typesafe.config.ConfigFactory

object AppConfig {
  private val config = ConfigFactory.load("application.conf")

  val bronzeHistoricalAirQuality: String = config.getString("bronze.historical_air_quality")
  val bronzeHistoricalWeatherDaily: String = config.getString("bronze.historical_weather_daily")

  val silverHistoricalAirQuality: String = config.getString("silver.historical_air_quality")
  val silverHistoricalWeatherDaily: String = config.getString("silver.historical_weather_daily")

  val europeanCitiesCsvPath: String = config.getString("european_cities_csv_path")
  val showRowNum: Int = config.getInt("showRowNum")

}
