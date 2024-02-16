package aqicn.joiner

import aqicn.avro.message.{AirQuality, City, AirQualityWithPollutionLevel, CityAqiInfo}

trait AqiCityJoiner {
  def joinAqiWithCity: (AirQualityWithPollutionLevel, City) => CityAqiInfo = {
    (aqi: AirQualityWithPollutionLevel, city: City) => {
      CityAqiInfo(
        airPollutantLevel = aqi.airPollutantLevel,
        url = aqi.url,
        stationName = aqi.stationName,
        dominentPol = aqi.dominentPol,
        aqiValue = aqi.aqi,
        index = aqi.index,
        co = aqi.iaqi.co,
        h = aqi.iaqi.h,
        no2 = aqi.iaqi.no2,
        o3 = aqi.iaqi.o3,
        p = aqi.iaqi.p,
        pm10 = aqi.iaqi.pm10,
        pm25 = aqi.iaqi.pm25,
        so2 = aqi.iaqi.so2,
        lat = city.lat,
        lon = city.lon,
        name = city.name,
        country = city.country,
        population = city.population,
        STM = aqi.STM
      )
    }
  }
}
