package aqicn.aggregate

import aqicn.`enum`.AirPollutionLevel
import aqicn.avro.message.{CityAqiInfo, CityMetric, CountryAirQualityMetrics}

trait CountryAirQualityMetricsAggregator {

  def aggregate(key: String, cityAqiInfo: CityAqiInfo, metric: CountryAirQualityMetrics): CountryAirQualityMetrics = {
    CountryAirQualityMetrics(
      country = cityAqiInfo.country,
      cityWithHighestPM10 = calcCityWithHighestPM10(cityAqiInfo, metric),
      cityWithHighestPM25 = calcCityWithHighestPM25(cityAqiInfo, metric),
      cityWithHighestAqi = calcCityWithHighestAqi(cityAqiInfo, metric),
      dominantPollutantInCountry = ???,
      averageCountryAqi = ???,
      numberOfCitiesWithHazardousAirPollutantLevel = calcNumberOfCitiesWithHazardousAirPollutantLevel(cityAqiInfo, metric),
      numberOfCitiesWithUnhealthyPollutantLevel = calcNumberOfCitiesWithUnhealthyPollutantLevel(cityAqiInfo, metric)
    )
  }

  private def calcNumberOfCitiesWithUnhealthyPollutantLevel(cityAqiInfo: CityAqiInfo, metric: CountryAirQualityMetrics): Int = {
    if (cityAqiInfo.airPollutantLevel == AirPollutionLevel.Unhealthy.toString ||
      cityAqiInfo.airPollutantLevel == AirPollutionLevel.VeryUnhealthy.toString) {
      metric.numberOfCitiesWithUnhealthyPollutantLevel = metric.numberOfCitiesWithUnhealthyPollutantLevel + 1
      metric.numberOfCitiesWithUnhealthyPollutantLevel
    } else {
      metric.numberOfCitiesWithUnhealthyPollutantLevel
    }
  }

  private def calcNumberOfCitiesWithHazardousAirPollutantLevel(cityAqiInfo: CityAqiInfo, metric: CountryAirQualityMetrics): Int = {
    if (cityAqiInfo.airPollutantLevel == AirPollutionLevel.Hazardous.toString) {
      metric.numberOfCitiesWithHazardousAirPollutantLevel = metric.numberOfCitiesWithHazardousAirPollutantLevel + 1
      metric.numberOfCitiesWithHazardousAirPollutantLevel
    } else {
      metric.numberOfCitiesWithHazardousAirPollutantLevel
    }
  }

  private def calcCityWithHighestAqi(cityAqiInfo: CityAqiInfo, metric: CountryAirQualityMetrics): CityMetric = {
    if (cityAqiInfo.aqiValue.get > metric.cityWithHighestAqi.value) {
      CityMetric(
        cityName = cityAqiInfo.name,
        value = cityAqiInfo.aqiValue.get,
        pollutant = cityAqiInfo.dominentPol,
        stationName = cityAqiInfo.stationName
      )
    } else {
      metric.cityWithHighestAqi
    }
  }

  private def calcCityWithHighestPM10(cityAqiInfo: CityAqiInfo, metric: CountryAirQualityMetrics): CityMetric = {
    if (cityAqiInfo.pm10.get > metric.cityWithHighestPM10.value) {
      CityMetric(
        cityName = cityAqiInfo.name,
        value = cityAqiInfo.pm10.get,
        pollutant = "pm10",
        stationName = cityAqiInfo.stationName
      )
    } else {
      metric.cityWithHighestPM10
    }
  }

  private def calcCityWithHighestPM25(cityAqiInfo: CityAqiInfo, metric: CountryAirQualityMetrics): CityMetric = {
    if (cityAqiInfo.pm25.get > metric.cityWithHighestPM25.value) {
      CityMetric(
        cityName = cityAqiInfo.name,
        value = cityAqiInfo.pm25.get,
        pollutant = "pm25",
        stationName = cityAqiInfo.stationName
      )
    } else {
      metric.cityWithHighestPM25
    }
  }

}
