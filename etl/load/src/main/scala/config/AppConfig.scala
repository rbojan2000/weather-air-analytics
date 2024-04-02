package config

import com.typesafe.config.ConfigFactory

object AppConfig {
  private val config = ConfigFactory.load("application.conf")

  val silverHistoricalAirQuality: String = config.getString("silver.historical_air_quality")
  val silverHistoricalWeatherDaily: String = config.getString("silver.historical_weather_daily")

  val showRowNum: Int = config.getInt("showRowNum")
}
