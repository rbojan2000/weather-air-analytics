package aqicn.mapper

import aqicn.`enum`.AirPollutionLevel
import aqicn.avro.message.{AirQuality, AirQualityWithPollutionLevel}
import aqicn.config.AirPollutantLevelLimits

trait AirQualityPollutantLevelMapper {

  implicit def airPollutantLevelLimits: AirPollutantLevelLimits

  def mapToCityAirPollutantLevel(): (String, AirQuality) => AirQualityWithPollutionLevel = {
    (_: String, v: AirQuality) => {
      AirQualityWithPollutionLevel(
        url = v.url,
        stationName = v.stationName,
        dominentPol = v.dominentPol,
        aqi = v.aqi,
        index = v.index,
        iaqi = v.iaqi,
        city = v.city,
        STM = v.STM,
        airPollutantLevel = callculateAirPollutantLevel(v.aqi)
      )
    }
  }

  private def callculateAirPollutantLevel(aqi: Option[Int])(implicit airPollutantLevelLimits: AirPollutantLevelLimits): String = {
    aqi match {
      case Some(value) =>
        if (value >= airPollutantLevelLimits.hazardous.lowLimit) AirPollutionLevel.Hazardous.toString
        else if (value >= airPollutantLevelLimits.veryUnhealthy.lowLimit) AirPollutionLevel.VeryUnhealthy.toString
        else if (value >= airPollutantLevelLimits.unhealthy.highLimit) AirPollutionLevel.Unhealthy.toString
        else if (value >= airPollutantLevelLimits.unhealthyForSensitiveGroups.highLimit) AirPollutionLevel.UnhealthyForSensitiveGroups.toString
        else if (value >= airPollutantLevelLimits.moderate.highLimit) AirPollutionLevel.Moderate.toString
        else AirPollutionLevel.Good.toString

    }
  }
}
