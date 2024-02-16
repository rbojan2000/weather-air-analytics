/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package aqicn.avro.message

import scala.annotation.switch

final case class CountryAirQualityMetrics(var country: String, var cityWithHighestPM10: aqicn.avro.message.CityMetric, var cityWithHighestPM25: aqicn.avro.message.CityMetric, var cityWithHighestAqi: aqicn.avro.message.CityMetric, var dominantPollutantInCountry: String, var averageCountryAqi: Long, var numberOfCitiesWithHazardousAirPollutantLevel: Int, var numberOfCitiesWithUnhealthyPollutantLevel: Int) extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this("", new CityMetric, new CityMetric, new CityMetric, "", 0L, 0, 0)
  def get(field$: Int): AnyRef = {
    (field$: @switch) match {
      case 0 => {
        country
      }.asInstanceOf[AnyRef]
      case 1 => {
        cityWithHighestPM10
      }.asInstanceOf[AnyRef]
      case 2 => {
        cityWithHighestPM25
      }.asInstanceOf[AnyRef]
      case 3 => {
        cityWithHighestAqi
      }.asInstanceOf[AnyRef]
      case 4 => {
        dominantPollutantInCountry
      }.asInstanceOf[AnyRef]
      case 5 => {
        averageCountryAqi
      }.asInstanceOf[AnyRef]
      case 6 => {
        numberOfCitiesWithHazardousAirPollutantLevel
      }.asInstanceOf[AnyRef]
      case 7 => {
        numberOfCitiesWithUnhealthyPollutantLevel
      }.asInstanceOf[AnyRef]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
  }
  def put(field$: Int, value: Any): Unit = {
    (field$: @switch) match {
      case 0 => this.country = {
        value.toString
      }.asInstanceOf[String]
      case 1 => this.cityWithHighestPM10 = {
        value
      }.asInstanceOf[aqicn.avro.message.CityMetric]
      case 2 => this.cityWithHighestPM25 = {
        value
      }.asInstanceOf[aqicn.avro.message.CityMetric]
      case 3 => this.cityWithHighestAqi = {
        value
      }.asInstanceOf[aqicn.avro.message.CityMetric]
      case 4 => this.dominantPollutantInCountry = {
        value.toString
      }.asInstanceOf[String]
      case 5 => this.averageCountryAqi = {
        value
      }.asInstanceOf[Long]
      case 6 => this.numberOfCitiesWithHazardousAirPollutantLevel = {
        value
      }.asInstanceOf[Int]
      case 7 => this.numberOfCitiesWithUnhealthyPollutantLevel = {
        value
      }.asInstanceOf[Int]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
    ()
  }
  def getSchema: org.apache.avro.Schema = aqicn.avro.message.CountryAirQualityMetrics.SCHEMA$
}

object CountryAirQualityMetrics {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"CountryAirQualityMetrics\",\"namespace\":\"aqicn.avro.message\",\"fields\":[{\"name\":\"country\",\"type\":\"string\"},{\"name\":\"cityWithHighestPM10\",\"type\":{\"type\":\"record\",\"name\":\"CityMetric\",\"fields\":[{\"name\":\"cityName\",\"type\":\"string\"},{\"name\":\"value\",\"type\":\"double\"},{\"name\":\"pollutant\",\"type\":\"string\"},{\"name\":\"stationName\",\"type\":\"string\"}]}},{\"name\":\"cityWithHighestPM25\",\"type\":\"CityMetric\"},{\"name\":\"cityWithHighestAqi\",\"type\":\"CityMetric\"},{\"name\":\"dominantPollutantInCountry\",\"type\":\"string\"},{\"name\":\"averageCountryAqi\",\"type\":\"long\"},{\"name\":\"numberOfCitiesWithHazardousAirPollutantLevel\",\"type\":\"int\"},{\"name\":\"numberOfCitiesWithUnhealthyPollutantLevel\",\"type\":\"int\"}]}")
}