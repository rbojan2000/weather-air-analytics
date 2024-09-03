package aqicn.aggregate

import aqicn.UnitTest
import aqicn.`enum`.AirPollutionLevel
import aqicn.avro.message.{CityAqiInfo, CityMetric, CountryAirQualityMetrics}

class CountryAirQualityMetricsAggregatorTest extends UnitTest with CountryAirQualityMetricsAggregator {

  behavior of "calcCityWithHighestPM10"
    it should "update the city with the highest PM10 value when a higher PM10 value is provided" in  {
      val newMeasureAqiInfo = CityAqiInfo(
        airPollutantLevel = AirPollutionLevel.Moderate.toString,
        url = "https://londonair.org.uk/",
        stationName = "UK-AIR, air quality information resource - Defra, UK",
        dominentPol = "pm10",
        aqiValue = Some(57),
        index = 1437,
        STM = Some(1725381306),
        co = Some(5.5),
        h = Some(28),
        no2 = Some(10.1),
        o3 = Some(28.5),
        p = Some(31),
        pm10 = Some(57),
        pm25 = Some(20),
        so2 = Some(11),
        lat = 31.2047372.toString,
        lon = 121.4489017.toString,
        name = "London",
        country = "United Kingdom",
        population = 8567000
      )

      val existingCountryMetric = CountryAirQualityMetrics(
        country = "United Kingdom",
        cityWithHighestPM10 = CityMetric(
          cityName = "Birmingham",
          value = 30,
          pollutant = "pm10",
          stationName="World Air Quality Index Project",
        ),
        cityWithHighestPM25 = CityMetric(
          cityName = "Newcastle",
          value = 40,
          pollutant = "pm25",
          stationName="Office of Environment and Heritage - NSW",
        ),
        cityWithHighestAqi = CityMetric(
          cityName = "Birmingham",
          value = 30,
          pollutant = "pm10",
          stationName="World Air Quality Index Project",
        ),
        dominantPollutantInCountry = "pm25",
        dominantPollutantCounts = Map("pm25" -> 40, "pm10" -> 57, "o3" -> 11),
        numberOfCitiesWithHazardousAirPollutantLevel = 1,
        numberOfCitiesWithUnhealthyPollutantLevel = 2
      )

      val result = calcCityWithHighestPM10(newMeasureAqiInfo, existingCountryMetric)

      result.cityName should be("London")
      result.value should be(57)
      result.pollutant should be("pm10")
      result.stationName should be("UK-AIR, air quality information resource - Defra, UK")
    }

    it should "keep the existing city with the highest PM10 value when a lower PM10 value is provided" in {
      val newMeasureAqiInfo = CityAqiInfo(
        airPollutantLevel = AirPollutionLevel.Moderate.toString,
        url = "https://londonair.org.uk/",
        stationName = "UK-AIR, air quality information resource - Defra, UK",
        dominentPol = "pm10",
        aqiValue = Some(25),
        index = 1437,
        STM = Some(1725381306),
        co = Some(5.5),
        h = Some(11),
        no2 = Some(10.1),
        o3 = Some(10.5),
        p = Some(31),
        pm10 = Some(25),
        pm25 = Some(20),
        so2 = Some(11),
        lat = 31.2047372.toString,
        lon = 121.4489017.toString,
        name = "London",
        country = "United Kingdom",
        population = 8567000
      )

      val existingCountryMetric = CountryAirQualityMetrics(
        country = "United Kingdom",
        cityWithHighestPM10 = CityMetric(
          cityName = "Birmingham",
          value = 30,
          pollutant = "pm10",
          stationName="World Air Quality Index Project",
        ),
        cityWithHighestPM25 = CityMetric(
          cityName = "Newcastle",
          value = 40,
          pollutant = "pm25",
          stationName="Office of Environment and Heritage - NSW",
        ),
        cityWithHighestAqi = CityMetric(
          cityName = "Birmingham",
          value = 30,
          pollutant = "pm10",
          stationName="World Air Quality Index Project",
        ),
        dominantPollutantInCountry = "pm25",
        dominantPollutantCounts = Map("pm25" -> 40, "pm10" -> 57, "o3" -> 11),
        numberOfCitiesWithHazardousAirPollutantLevel = 1,
        numberOfCitiesWithUnhealthyPollutantLevel = 2
      )

      val result = calcCityWithHighestPM10(newMeasureAqiInfo, existingCountryMetric)

      result.cityName should be("Birmingham")
      result.value should be(30)
      result.pollutant should be("pm10")
      result.stationName should be("World Air Quality Index Project")
    }
    
}
