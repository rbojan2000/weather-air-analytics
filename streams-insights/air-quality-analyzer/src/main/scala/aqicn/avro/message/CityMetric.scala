/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package aqicn.avro.message

import scala.annotation.switch

final case class CityMetric(var cityName: String, var value: Double, var pollutant: String, var stationName: String) extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this("", 0.0, "", "")
  def get(field$: Int): AnyRef = {
    (field$: @switch) match {
      case 0 => {
        cityName
      }.asInstanceOf[AnyRef]
      case 1 => {
        value
      }.asInstanceOf[AnyRef]
      case 2 => {
        pollutant
      }.asInstanceOf[AnyRef]
      case 3 => {
        stationName
      }.asInstanceOf[AnyRef]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
  }
  def put(field$: Int, value: Any): Unit = {
    (field$: @switch) match {
      case 0 => this.cityName = {
        value.toString
      }.asInstanceOf[String]
      case 1 => this.value = {
        value
      }.asInstanceOf[Double]
      case 2 => this.pollutant = {
        value.toString
      }.asInstanceOf[String]
      case 3 => this.stationName = {
        value.toString
      }.asInstanceOf[String]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
    ()
  }
  def getSchema: org.apache.avro.Schema = aqicn.avro.message.CityMetric.SCHEMA$
}

object CityMetric {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"CityMetric\",\"namespace\":\"aqicn.avro.message\",\"fields\":[{\"name\":\"cityName\",\"type\":\"string\"},{\"name\":\"value\",\"type\":\"double\"},{\"name\":\"pollutant\",\"type\":\"string\"},{\"name\":\"stationName\",\"type\":\"string\"}]}")
}