package aqicn.aggregate

import aqicn.UnitTest
import aqicn.`enum`.AirPollutionLevel
import aqicn.avro.message.{CityAqiInfo, CityMetric, CountryAirQualityMetrics}

class CountryAirQualityMetricsAggregatorTest extends UnitTest with CountryAirQualityMetricsAggregator {

  behavior of "getDominantPollutantInCountry"
  it should "return the pollutant with the highest count when there is a clear dominant pollutant" in {
    val countryAirQualityMetric = CountryAirQualityMetrics(
      country = "United Kingdom",
      cityWithHighestPM10 = null,
      cityWithHighestPM25 = null,
      cityWithHighestAqi = null,
      dominantPollutantInCountry = "",
      dominantPollutantCounts = Map("pm10" -> 70, "pm25" -> 50, "o3" -> 30),
      numberOfCitiesWithHazardousAirPollutantLevel = 0,
      numberOfCitiesWithUnhealthyPollutantLevel = 0
    )
    val result = getDominantPollutantInCountry(countryAirQualityMetric)

    result should be("pm10")
  }

  it should "return an empty string when no pollutants are recorded" in {
    val countryAirQualityMetric = CountryAirQualityMetrics(
      country = "United Kingdom",
      cityWithHighestPM10 = null,
      cityWithHighestPM25 = null,
      cityWithHighestAqi = null,
      dominantPollutantInCountry = "",
      dominantPollutantCounts = Map.empty,
      numberOfCitiesWithHazardousAirPollutantLevel = 0,
      numberOfCitiesWithUnhealthyPollutantLevel = 0
    )

    val result = getDominantPollutantInCountry(countryAirQualityMetric)

    result should be("")
  }

  it should "return the only pollutant present when there is a single entry in the map" in {
    val countryAirQualityMetric = CountryAirQualityMetrics(
      country = "United Kingdom",
      cityWithHighestPM10 = null,
      cityWithHighestPM25 = null,
      cityWithHighestAqi = null,
      dominantPollutantInCountry = "",
      dominantPollutantCounts = Map("pm25" -> 85),
      numberOfCitiesWithHazardousAirPollutantLevel = 0,
      numberOfCitiesWithUnhealthyPollutantLevel = 0
    )

    val result = getDominantPollutantInCountry(countryAirQualityMetric)

    result should be("pm25")
  }

  behavior of "updateDominantPollutantCounts"
  it should "add a new dominant pollutant to an empty map" in {
    val cityAqiInfo = CityAqiInfo(
      airPollutantLevel = AirPollutionLevel.Moderate.toString,
      url = "https://londonair.org.uk/",
      stationName = "UK-AIR, air quality information resource - Defra, UK",
      dominentPol = "pm10",
      aqiValue = Some(45),
      index = 1,
      STM = Some(1234567890),
      co = Some(0.5),
      h = Some(25),
      no2 = Some(10.0),
      o3 = Some(20.0),
      p = Some(5),
      pm10 = Some(45),
      pm25 = Some(20),
      so2 = Some(8),
      lat = 31.2047372.toString,
      lon = 121.4489017.toString,
      name = "London",
      country = "United Kingdom",
      population = 8567000
    )

    val metric = CountryAirQualityMetrics(
      country = "CountryA",
      cityWithHighestPM10 = null,
      cityWithHighestPM25 = null,
      cityWithHighestAqi = null,
      dominantPollutantInCountry = "",
      dominantPollutantCounts = Map.empty,
      numberOfCitiesWithHazardousAirPollutantLevel = 0,
      numberOfCitiesWithUnhealthyPollutantLevel = 0
    )

    val result = updateDominantPollutantCounts(cityAqiInfo, metric)

    result should be(Map("pm10" -> 1))
  }

  it should "update the count of an existing dominant pollutant" in {
    val cityAqiInfo = CityAqiInfo(
      airPollutantLevel = AirPollutionLevel.Moderate.toString,
      url = "https://londonair.org.uk/",
      stationName = "UK-AIR, air quality information resource - Defra, UK",
      dominentPol = "pm10",
      aqiValue = Some(55),
      index = 1,
      STM = Some(1234567890),
      co = Some(0.6),
      h = Some(30),
      no2 = Some(15.0),
      o3 = Some(25.0),
      p = Some(10),
      pm10 = Some(55),
      pm25 = Some(25),
      so2 = Some(10),
      lat = 31.2047372.toString,
      lon = 121.4489017.toString,
      name = "London",
      country = "United Kingdom",
      population = 8567000
    )

    val metric = CountryAirQualityMetrics(
      country = "Uninted Kingdom",
      cityWithHighestPM10 = null,
      cityWithHighestPM25 = null,
      cityWithHighestAqi = null,
      dominantPollutantInCountry = "",
      dominantPollutantCounts = Map("pm10" -> 2, "pm25" -> 3),
      numberOfCitiesWithHazardousAirPollutantLevel = 0,
      numberOfCitiesWithUnhealthyPollutantLevel = 0
    )

    val result = updateDominantPollutantCounts(cityAqiInfo, metric)

    result should be(Map("pm10" -> 3, "pm25" -> 3))
  }

  it should "add a new dominant pollutant to a non-empty map" in {
    val cityAqiInfo = CityAqiInfo(
      airPollutantLevel = AirPollutionLevel.Good.toString,
      url = "https://londonair.org.uk/",
      stationName = "UK-AIR, air quality information resource - Defra, UK",
      dominentPol = "o3",
      aqiValue = Some(30),
      index = 2,
      STM = Some(234678901),
      co = Some(0.3),
      h = Some(20),
      no2 = Some(8.0),
      o3 = Some(30),
      p = Some(8),
      pm10 = Some(20),
      pm25 = Some(15),
      so2 = Some(6),
      lat = 31.2047372.toString,
      lon = 121.4489017.toString,
      name = "London",
      country = "United Kingdom",
      population = 8567000
    )

    val metric = CountryAirQualityMetrics(
      country = "United Kingdom",
      cityWithHighestPM10 = null,
      cityWithHighestPM25 = null,
      cityWithHighestAqi = null,
      dominantPollutantInCountry = "",
      dominantPollutantCounts = Map("pm10" -> 4, "pm25" -> 2),
      numberOfCitiesWithHazardousAirPollutantLevel = 0,
      numberOfCitiesWithUnhealthyPollutantLevel = 0
    )

    val result = updateDominantPollutantCounts(cityAqiInfo, metric)

    result should be(Map("pm10" -> 4, "pm25" -> 2, "o3" -> 1))
  }

  it should "increment the count for an existing dominant pollutant in a map with multiple entries" in {
    val cityAqiInfo = CityAqiInfo(
      airPollutantLevel = AirPollutionLevel.Unhealthy.toString,
      url = "https://londonair.org.uk/",
      stationName = "UK-AIR, air quality information resource - Defra, UK",
      dominentPol = "pm25",
      aqiValue = Some(120),
      index = 3,
      STM = Some(345679012),
      co = Some(0.7),
      h = Some(40),
      no2 = Some(20.0),
      o3 = Some(35.0),
      p = Some(12),
      pm10 = Some(45),
      pm25 = Some(120),
      so2 = Some(12),
      lat = 31.2047372.toString,
      lon = 121.4489017.toString,
      name = "London",
      country = "United Kingdom",
      population = 8567000
    )

    val metric = CountryAirQualityMetrics(
      country = "United Kingdom",
      cityWithHighestPM10 = null,
      cityWithHighestPM25 = null,
      cityWithHighestAqi = null,
      dominantPollutantInCountry = "",
      dominantPollutantCounts = Map("pm10" -> 5, "pm25" -> 2, "o3" -> 3),
      numberOfCitiesWithHazardousAirPollutantLevel = 0,
      numberOfCitiesWithUnhealthyPollutantLevel = 0
    )

    val result = updateDominantPollutantCounts(cityAqiInfo, metric)

    result should be(Map("pm10" -> 5, "pm25" -> 3, "o3" -> 3))
  }

  behavior of "calcNumberOfCitiesWithUnhealthyPollutantLevel"
  it should "increment the count when air pollutant level is Unhealthy" in {
    val cityAqiInfo = CityAqiInfo(
      airPollutantLevel = AirPollutionLevel.Unhealthy.toString,
      url = "https://londonair.org.uk/",
      stationName = "UK-AIR, air quality information resource - Defra, UK",
      dominentPol = "pm10",
      aqiValue = Some(150),
      index = 1,
      STM = Some(1234567890),
      co = Some(0.6),
      h = Some(30),
      no2 = Some(20.0),
      o3 = Some(40.0),
      p = Some(10),
      pm10 = Some(150),
      pm25 = Some(70),
      so2 = Some(15),
      lat = 31.2047372.toString,
      lon = 121.4489017.toString,
      name = "London",
      country = "United Kingdom",
      population = 8567000
    )

    val metric = CountryAirQualityMetrics(
      country = "Uninted Kingdom",
      cityWithHighestPM10 = null,
      cityWithHighestPM25 = null,
      cityWithHighestAqi = null,
      dominantPollutantInCountry = "",
      dominantPollutantCounts = Map.empty,
      numberOfCitiesWithHazardousAirPollutantLevel = 0,
      numberOfCitiesWithUnhealthyPollutantLevel = 5
    )

    val result = calcNumberOfCitiesWithUnhealthyPollutantLevel(cityAqiInfo, metric)

    result should be(6)
  }

  it should "increment the count when air pollutant level is Very Unhealthy" in {
    val cityAqiInfo = CityAqiInfo(
      airPollutantLevel = AirPollutionLevel.VeryUnhealthy.toString,
      url = "https://londonair.org.uk/",
      stationName = "UK-AIR, air quality information resource - Defra, UK",
      dominentPol = "pm25",
      aqiValue = Some(200),
      index = 2,
      STM = Some(234567891),
      co = Some(0.7),
      h = Some(40),
      no2 = Some(25.0),
      o3 = Some(50.0),
      p = Some(15),
      pm10 = Some(180),
      pm25 = Some(200),
      so2 = Some(20),
      lat = 31.2047372.toString,
      lon = 121.4489017.toString,
      name = "London",
      country = "United Kingdom",
      population = 8567000
    )

    val metric = CountryAirQualityMetrics(
      country = "United Kingdom",
      cityWithHighestPM10 = null,
      cityWithHighestPM25 = null,
      cityWithHighestAqi = null,
      dominantPollutantInCountry = "",
      dominantPollutantCounts = Map.empty,
      numberOfCitiesWithHazardousAirPollutantLevel = 0,
      numberOfCitiesWithUnhealthyPollutantLevel = 3
    )

    val result = calcNumberOfCitiesWithUnhealthyPollutantLevel(cityAqiInfo, metric)

    result should be(4)
  }

  it should "not change the count when air pollutant level is not Unhealthy or Very Unhealthy" in {
    val cityAqiInfo = CityAqiInfo(
      airPollutantLevel = AirPollutionLevel.Moderate.toString,
      url = "https://londonair.org.uk/",
      stationName = "UK-AIR, air quality information resource - Defra, UK",
      dominentPol = "o3",
      aqiValue = Some(60),
      index = 3,
      STM = Some(345689012),
      co = Some(0.5),
      h = Some(25),
      no2 = Some(15.0),
      o3 = Some(30.0),
      p = Some(8),
      pm10 = Some(30),
      pm25 = Some(25),
      so2 = Some(10),
      lat = 31.2047372.toString,
      lon = 121.4489017.toString,
      name = "London",
      country = "United Kingdom",
      population = 8567000
    )

    val metric = CountryAirQualityMetrics(
      country = "United Kingdom",
      cityWithHighestPM10 = null,
      cityWithHighestPM25 = null,
      cityWithHighestAqi = null,
      dominantPollutantInCountry = "",
      dominantPollutantCounts = Map.empty,
      numberOfCitiesWithHazardousAirPollutantLevel = 0,
      numberOfCitiesWithUnhealthyPollutantLevel = 10
    )

    val result = calcNumberOfCitiesWithUnhealthyPollutantLevel(cityAqiInfo, metric)

    result should be(10)
  }

  it should "correctly handle the initial count of zero when air pollutant level is Unhealthy" in {
    val cityAqiInfo = CityAqiInfo(
      airPollutantLevel = AirPollutionLevel.Unhealthy.toString,
      url = "https://londonair.org.uk/",
      stationName = "UK-AIR, air quality information resource - Defra, UK",
      dominentPol = "pm10",
      aqiValue = Some(150),
      index = 4,
      STM = Some(456790123),
      co = Some(0.8),
      h = Some(35),
      no2 = Some(18.0),
      o3 = Some(45.0),
      p = Some(12),
      pm10 = Some(160),
      pm25 = Some(75),
      so2 = Some(18),
      lat = 31.2047372.toString,
      lon = 121.4489017.toString,
      name = "London",
      country = "United Kingdom",
      population = 8567000
    )

    val metric = CountryAirQualityMetrics(
      country = "United Kingdom",
      cityWithHighestPM10 = null,
      cityWithHighestPM25 = null,
      cityWithHighestAqi = null,
      dominantPollutantInCountry = "",
      dominantPollutantCounts = Map.empty,
      numberOfCitiesWithHazardousAirPollutantLevel = 0,
      numberOfCitiesWithUnhealthyPollutantLevel = 0
    )

    val result = calcNumberOfCitiesWithUnhealthyPollutantLevel(cityAqiInfo, metric)

    result should be(1)
  }

  behavior of "calcCityWithHighestPM10"
  it should "update the city with the highest PM10 value when a higher PM10 value is provided" in {
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
        stationName = "World Air Quality Index Project",
      ),
      cityWithHighestPM25 = CityMetric(
        cityName = "Newcastle",
        value = 40,
        pollutant = "pm25",
        stationName = "Office of Environment and Heritage - NSW",
      ),
      cityWithHighestAqi = CityMetric(
        cityName = "Birmingham",
        value = 30,
        pollutant = "pm10",
        stationName = "World Air Quality Index Project",
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
        stationName = "World Air Quality Index Project",
      ),
      cityWithHighestPM25 = CityMetric(
        cityName = "Newcastle",
        value = 40,
        pollutant = "pm25",
        stationName = "Office of Environment and Heritage - NSW",
      ),
      cityWithHighestAqi = CityMetric(
        cityName = "Birmingham",
        value = 30,
        pollutant = "pm10",
        stationName = "World Air Quality Index Project",
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

  behavior of "calcCityWithHighestAqi"
  it should "return the new city metric when the new AQI value is higher than the existing one" in {
    val newCityAqiInfo = CityAqiInfo(
      airPollutantLevel = AirPollutionLevel.Moderate.toString,
      url = "https://londonair.org.uk/",
      stationName = "UK-AIR, air quality information resource - Defra, UK",
      dominentPol = "pm10",
      aqiValue = Some(120),
      index = 1,
      STM = Some(1234567890),
      co = Some(0.5),
      h = Some(25),
      no2 = Some(10.0),
      o3 = Some(20.0),
      p = Some(5),
      pm10 = Some(50),
      pm25 = Some(30),
      so2 = Some(8),
      lat = 31.2047372.toString,
      lon = 121.4489017.toString,
      name = "London",
      country = "United Kingdom",
      population = 8567000
    )

    val existingMetric = CountryAirQualityMetrics(
      country = "United Kingdom",
      cityWithHighestPM10 = null,
      cityWithHighestPM25 = null,
      cityWithHighestAqi = CityMetric(
        cityName = "Newcastle",
        value = 100,
        pollutant = "pm10",
        stationName = "Office of Environment and Heritage - NSW",
      ),
      dominantPollutantInCountry = "",
      dominantPollutantCounts = Map.empty,
      numberOfCitiesWithHazardousAirPollutantLevel = 0,
      numberOfCitiesWithUnhealthyPollutantLevel = 0
    )

    val result = calcCityWithHighestAqi(newCityAqiInfo, existingMetric)

    result should be(CityMetric(
      cityName = "London",
      value = 120,
      pollutant = "pm10",
      stationName = "UK-AIR, air quality information resource - Defra, UK"
    ))
  }

  it should "return the existing city metric when the new AQI value is lower than the current highest" in {
    val newCityAqiInfo = CityAqiInfo(
      airPollutantLevel = AirPollutionLevel.Moderate.toString,
      url = "https://londonair.org.uk/",
      stationName = "UK-AIR, air quality information resource - Defra, UK",
      dominentPol = "pm10",
      aqiValue = Some(80),
      index = 3,
      STM = Some(346789012),
      co = Some(0.7),
      h = Some(35),
      no2 = Some(20.0),
      o3 = Some(30.0),
      p = Some(12),
      pm10 = Some(45),
      pm25 = Some(25),
      so2 = Some(15),
      lat = 31.2047372.toString,
      lon = 121.4489017.toString,
      name = "London",
      country = "United Kingdom",
      population = 8567000
    )

    val existingMetric = CountryAirQualityMetrics(
      country = "United Kingdom",
      cityWithHighestPM10 = null,
      cityWithHighestPM25 = null,
      cityWithHighestAqi = CityMetric(
        cityName = "Newcastle",
        value = 110,
        pollutant = "pm10",
        stationName = "Office of Environment and Heritage - NSW",
      ),
      dominantPollutantInCountry = "",
      dominantPollutantCounts = Map.empty,
      numberOfCitiesWithHazardousAirPollutantLevel = 0,
      numberOfCitiesWithUnhealthyPollutantLevel = 0
    )

    val result = calcCityWithHighestAqi(newCityAqiInfo, existingMetric)

    result should be(CityMetric(
      cityName = "Newcastle",
      value = 110,
      pollutant = "pm10",
      stationName = "Office of Environment and Heritage - NSW"
    ))
  }

}
