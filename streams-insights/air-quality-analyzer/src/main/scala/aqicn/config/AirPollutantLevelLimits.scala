package aqicn.config

import com.typesafe.config.{Config, ConfigFactory}

case class AirPollutantLevelLimits(
                                    good: LimitRange,
                                    moderate: LimitRange,
                                    unhealthyForSensitiveGroups: LimitRange,
                                    unhealthy: LimitRange,
                                    veryUnhealthy: LimitRange,
                                    hazardous: LimitRange
                                  )

case class LimitRange(lowLimit: Int, highLimit: Int)

object AirPollutantLevelLimits {
  def apply(): AirPollutantLevelLimits = {
    val config: Config = ConfigFactory.load("pollutantLevels.conf")
    val levelsConfig = config.getConfig("air-pollution-level")

    AirPollutantLevelLimits(
      good = readLimitRange(levelsConfig, "good"),
      moderate = readLimitRange(levelsConfig, "moderate"),
      unhealthyForSensitiveGroups = readLimitRange(levelsConfig, "unhealthly_for_sensitive_groups"),
      unhealthy = readLimitRange(levelsConfig, "unhealthly"),
      veryUnhealthy = readLimitRange(levelsConfig, "very_unhealthly"),
      hazardous = readLimitRange(levelsConfig, "hazardous")
    )
  }

  private def readLimitRange(config: Config, level: String): LimitRange = {
    val levelConfig = config.getConfig(level)
    LimitRange(
      lowLimit = levelConfig.getInt("low-limit"),
      highLimit = levelConfig.getInt("high-limit")
    )
  }
}
