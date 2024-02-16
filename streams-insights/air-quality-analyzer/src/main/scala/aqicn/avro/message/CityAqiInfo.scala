/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package aqicn.avro.message

import scala.annotation.switch

/**
 * @param airPollutantLevel The air pollutant level in the city
 * @param url 
 * @param stationName 
 * @param dominentPol 
 * @param aqiValue 
 * @param index 
 * @param STM 
 * @param co 
 * @param h 
 * @param no2 
 * @param o3 
 * @param p 
 * @param pm10 
 * @param pm25 
 * @param so2 
 * @param lat 
 * @param lon 
 * @param name 
 * @param country 
 * @param population 
 */
final case class CityAqiInfo(var airPollutantLevel: String, var url: String, var stationName: String, var dominentPol: String, var aqiValue: Option[Int], var index: Int, var STM: Option[Long], var co: Option[Double], var h: Option[Double], var no2: Option[Double], var o3: Option[Double], var p: Option[Double], var pm10: Option[Double], var pm25: Option[Double], var so2: Option[Double], var lat: String, var lon: String, var name: String, var country: String, var population: Long) extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this("", "", "", "", None, 0, None, None, None, None, None, None, None, None, None, "", "", "", "", 0L)
  def get(field$: Int): AnyRef = {
    (field$: @switch) match {
      case 0 => {
        airPollutantLevel
      }.asInstanceOf[AnyRef]
      case 1 => {
        url
      }.asInstanceOf[AnyRef]
      case 2 => {
        stationName
      }.asInstanceOf[AnyRef]
      case 3 => {
        dominentPol
      }.asInstanceOf[AnyRef]
      case 4 => {
        aqiValue match {
          case Some(x) => x
          case None => null
        }
      }.asInstanceOf[AnyRef]
      case 5 => {
        index
      }.asInstanceOf[AnyRef]
      case 6 => {
        STM match {
          case Some(x) => x
          case None => null
        }
      }.asInstanceOf[AnyRef]
      case 7 => {
        co match {
          case Some(x) => x
          case None => null
        }
      }.asInstanceOf[AnyRef]
      case 8 => {
        h match {
          case Some(x) => x
          case None => null
        }
      }.asInstanceOf[AnyRef]
      case 9 => {
        no2 match {
          case Some(x) => x
          case None => null
        }
      }.asInstanceOf[AnyRef]
      case 10 => {
        o3 match {
          case Some(x) => x
          case None => null
        }
      }.asInstanceOf[AnyRef]
      case 11 => {
        p match {
          case Some(x) => x
          case None => null
        }
      }.asInstanceOf[AnyRef]
      case 12 => {
        pm10 match {
          case Some(x) => x
          case None => null
        }
      }.asInstanceOf[AnyRef]
      case 13 => {
        pm25 match {
          case Some(x) => x
          case None => null
        }
      }.asInstanceOf[AnyRef]
      case 14 => {
        so2 match {
          case Some(x) => x
          case None => null
        }
      }.asInstanceOf[AnyRef]
      case 15 => {
        lat
      }.asInstanceOf[AnyRef]
      case 16 => {
        lon
      }.asInstanceOf[AnyRef]
      case 17 => {
        name
      }.asInstanceOf[AnyRef]
      case 18 => {
        country
      }.asInstanceOf[AnyRef]
      case 19 => {
        population
      }.asInstanceOf[AnyRef]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
  }
  def put(field$: Int, value: Any): Unit = {
    (field$: @switch) match {
      case 0 => this.airPollutantLevel = {
        value.toString
      }.asInstanceOf[String]
      case 1 => this.url = {
        value.toString
      }.asInstanceOf[String]
      case 2 => this.stationName = {
        value.toString
      }.asInstanceOf[String]
      case 3 => this.dominentPol = {
        value.toString
      }.asInstanceOf[String]
      case 4 => this.aqiValue = {
        value match {
          case null => None
          case _ => Some(value)
        }
      }.asInstanceOf[Option[Int]]
      case 5 => this.index = {
        value
      }.asInstanceOf[Int]
      case 6 => this.STM = {
        value match {
          case null => None
          case _ => Some(value)
        }
      }.asInstanceOf[Option[Long]]
      case 7 => this.co = {
        value match {
          case null => None
          case _ => Some(value)
        }
      }.asInstanceOf[Option[Double]]
      case 8 => this.h = {
        value match {
          case null => None
          case _ => Some(value)
        }
      }.asInstanceOf[Option[Double]]
      case 9 => this.no2 = {
        value match {
          case null => None
          case _ => Some(value)
        }
      }.asInstanceOf[Option[Double]]
      case 10 => this.o3 = {
        value match {
          case null => None
          case _ => Some(value)
        }
      }.asInstanceOf[Option[Double]]
      case 11 => this.p = {
        value match {
          case null => None
          case _ => Some(value)
        }
      }.asInstanceOf[Option[Double]]
      case 12 => this.pm10 = {
        value match {
          case null => None
          case _ => Some(value)
        }
      }.asInstanceOf[Option[Double]]
      case 13 => this.pm25 = {
        value match {
          case null => None
          case _ => Some(value)
        }
      }.asInstanceOf[Option[Double]]
      case 14 => this.so2 = {
        value match {
          case null => None
          case _ => Some(value)
        }
      }.asInstanceOf[Option[Double]]
      case 15 => this.lat = {
        value.toString
      }.asInstanceOf[String]
      case 16 => this.lon = {
        value.toString
      }.asInstanceOf[String]
      case 17 => this.name = {
        value.toString
      }.asInstanceOf[String]
      case 18 => this.country = {
        value.toString
      }.asInstanceOf[String]
      case 19 => this.population = {
        value
      }.asInstanceOf[Long]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
    ()
  }
  def getSchema: org.apache.avro.Schema = aqicn.avro.message.CityAqiInfo.SCHEMA$
}

object CityAqiInfo {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"CityAqiInfo\",\"namespace\":\"aqicn.avro.message\",\"fields\":[{\"name\":\"airPollutantLevel\",\"type\":\"string\",\"doc\":\"The air pollutant level in the city\",\"symbols\":[\"Good\",\"Moderate\",\"UnhealthyForSensitiveGroups\",\"Unhealthy\",\"VeryUnhealthy\",\"Hazardous\"]},{\"name\":\"url\",\"type\":\"string\"},{\"name\":\"stationName\",\"type\":\"string\"},{\"name\":\"dominentPol\",\"type\":\"string\"},{\"name\":\"aqiValue\",\"type\":[\"int\",\"null\"]},{\"name\":\"index\",\"type\":\"int\"},{\"name\":\"STM\",\"type\":[\"long\",\"null\"]},{\"name\":\"co\",\"type\":[\"double\",\"null\"]},{\"name\":\"h\",\"type\":[\"double\",\"null\"]},{\"name\":\"no2\",\"type\":[\"double\",\"null\"]},{\"name\":\"o3\",\"type\":[\"double\",\"null\"]},{\"name\":\"p\",\"type\":[\"double\",\"null\"]},{\"name\":\"pm10\",\"type\":[\"double\",\"null\"]},{\"name\":\"pm25\",\"type\":[\"double\",\"null\"]},{\"name\":\"so2\",\"type\":[\"double\",\"null\"]},{\"name\":\"lat\",\"type\":\"string\"},{\"name\":\"lon\",\"type\":\"string\"},{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"country\",\"type\":\"string\"},{\"name\":\"population\",\"type\":\"long\"}]}")
}