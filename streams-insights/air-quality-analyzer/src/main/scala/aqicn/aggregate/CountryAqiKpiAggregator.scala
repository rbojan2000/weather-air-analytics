package aqicn.aggregate

import aqicn.domain.{CityAqiInfo, CountryAirQualityMetrics}

trait CountryAqiKpiAggregator {

  def aggregate(key: String, cityAqiInfo: CityAqiInfo, kpi: CountryAirQualityMetrics): CountryAirQualityMetrics = {
    CountryAirQualityMetrics(country = ???, cityWithHighestPM10 = ???, cityWithHighestPM25 = ???, cityWithHighestAqi = ???, aqiValue = ???, dominantPollutant = ???, dominantPollutantInCountry = ???, averageCountryAqi = ???)
  }

  def merge(key: String, previousKpi: CountryAirQualityMetrics, newKpi: CountryAirQualityMetrics): CountryAirQualityMetrics = {
    CountryAirQualityMetrics(country = ???, cityWithHighestPM10 = ???, cityWithHighestPM25 = ???, cityWithHighestAqi = ???, aqiValue = ???, dominantPollutant = ???, dominantPollutantInCountry = ???, averageCountryAqi = ???)
  }
}