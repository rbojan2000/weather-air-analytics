/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package aqicn.avro.message

import scala.annotation.switch

/**
 * @param url 
 * @param stationName 
 * @param dominentPol 
 * @param aqi 
 * @param index 
 * @param STM 
 * @param iaqi 
 * @param city 
 * @param airPollutantLevel The air pollutant level in the city
 */
final case class AirQualityWithPollutionLevel(var url: String, var stationName: String, var dominentPol: String, var aqi: Option[Int] = None, var index: Int, var STM: Option[Long] = None, var iaqi: aqicn.avro.message.Iaqi, var city: aqicn.avro.message.City, var airPollutantLevel: String) extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this("", "", "", None, 0, None, new Iaqi, new City, "")
  def get(field$: Int): AnyRef = {
    (field$: @switch) match {
      case 0 => {
        url
      }.asInstanceOf[AnyRef]
      case 1 => {
        stationName
      }.asInstanceOf[AnyRef]
      case 2 => {
        dominentPol
      }.asInstanceOf[AnyRef]
      case 3 => {
        aqi match {
          case Some(x) => x
          case None => null
        }
      }.asInstanceOf[AnyRef]
      case 4 => {
        index
      }.asInstanceOf[AnyRef]
      case 5 => {
        STM match {
          case Some(x) => x
          case None => null
        }
      }.asInstanceOf[AnyRef]
      case 6 => {
        iaqi
      }.asInstanceOf[AnyRef]
      case 7 => {
        city
      }.asInstanceOf[AnyRef]
      case 8 => {
        airPollutantLevel
      }.asInstanceOf[AnyRef]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
  }
  def put(field$: Int, value: Any): Unit = {
    (field$: @switch) match {
      case 0 => this.url = {
        value.toString
      }.asInstanceOf[String]
      case 1 => this.stationName = {
        value.toString
      }.asInstanceOf[String]
      case 2 => this.dominentPol = {
        value.toString
      }.asInstanceOf[String]
      case 3 => this.aqi = {
        value match {
          case null => None
          case _ => Some(value)
        }
      }.asInstanceOf[Option[Int]]
      case 4 => this.index = {
        value
      }.asInstanceOf[Int]
      case 5 => this.STM = {
        value match {
          case null => None
          case _ => Some(value)
        }
      }.asInstanceOf[Option[Long]]
      case 6 => this.iaqi = {
        value
      }.asInstanceOf[aqicn.avro.message.Iaqi]
      case 7 => this.city = {
        value
      }.asInstanceOf[aqicn.avro.message.City]
      case 8 => this.airPollutantLevel = {
        value.toString
      }.asInstanceOf[String]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
    ()
  }
  def getSchema: org.apache.avro.Schema = aqicn.avro.message.AirQualityWithPollutionLevel.SCHEMA$
}

object AirQualityWithPollutionLevel {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"AirQualityWithPollutionLevel\",\"namespace\":\"aqicn.avro.message\",\"fields\":[{\"name\":\"url\",\"type\":\"string\"},{\"name\":\"stationName\",\"type\":\"string\"},{\"name\":\"dominentPol\",\"type\":\"string\"},{\"name\":\"aqi\",\"type\":[\"null\",\"int\"],\"default\":null},{\"name\":\"index\",\"type\":\"int\"},{\"name\":\"STM\",\"type\":[\"null\",\"long\"],\"default\":null},{\"name\":\"iaqi\",\"type\":{\"type\":\"record\",\"name\":\"Iaqi\",\"fields\":[{\"name\":\"co\",\"type\":[\"null\",\"double\"],\"default\":null},{\"name\":\"h\",\"type\":[\"null\",\"double\"],\"default\":null},{\"name\":\"no2\",\"type\":[\"null\",\"double\"],\"default\":null},{\"name\":\"o3\",\"type\":[\"null\",\"double\"],\"default\":null},{\"name\":\"p\",\"type\":[\"null\",\"double\"],\"default\":null},{\"name\":\"pm10\",\"type\":[\"null\",\"double\"],\"default\":null},{\"name\":\"pm25\",\"type\":[\"null\",\"double\"],\"default\":null},{\"name\":\"so2\",\"type\":[\"null\",\"double\"],\"default\":null}]}},{\"name\":\"city\",\"type\":{\"type\":\"record\",\"name\":\"City\",\"fields\":[{\"name\":\"id\",\"type\":\"int\"},{\"name\":\"lat\",\"type\":\"string\"},{\"name\":\"lon\",\"type\":\"string\"},{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"country\",\"type\":\"string\"},{\"name\":\"population\",\"type\":\"long\"}]}},{\"name\":\"airPollutantLevel\",\"type\":\"string\",\"doc\":\"The air pollutant level in the city\",\"symbols\":[\"Good\",\"Moderate\",\"UnhealthyForSensitiveGroups\",\"Unhealthy\",\"VeryUnhealthy\",\"Hazardous\"]}]}")
}