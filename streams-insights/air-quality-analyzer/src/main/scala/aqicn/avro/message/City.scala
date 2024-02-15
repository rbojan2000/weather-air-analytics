/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package aqicn.avro.message

import scala.annotation.switch

final case class City(var id: Int, var lat: String, var lon: String, var name: String, var country: String, var population: Long) extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this(0, "", "", "", "", 0L)
  def get(field$: Int): AnyRef = {
    (field$: @switch) match {
      case 0 => {
        id
      }.asInstanceOf[AnyRef]
      case 1 => {
        lat
      }.asInstanceOf[AnyRef]
      case 2 => {
        lon
      }.asInstanceOf[AnyRef]
      case 3 => {
        name
      }.asInstanceOf[AnyRef]
      case 4 => {
        country
      }.asInstanceOf[AnyRef]
      case 5 => {
        population
      }.asInstanceOf[AnyRef]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
  }
  def put(field$: Int, value: Any): Unit = {
    (field$: @switch) match {
      case 0 => this.id = {
        value
      }.asInstanceOf[Int]
      case 1 => this.lat = {
        value.toString
      }.asInstanceOf[String]
      case 2 => this.lon = {
        value.toString
      }.asInstanceOf[String]
      case 3 => this.name = {
        value.toString
      }.asInstanceOf[String]
      case 4 => this.country = {
        value.toString
      }.asInstanceOf[String]
      case 5 => this.population = {
        value
      }.asInstanceOf[Long]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
    ()
  }
  def getSchema: org.apache.avro.Schema = aqicn.avro.message.City.SCHEMA$
}

object City {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"City\",\"namespace\":\"aqicn.avro.message\",\"fields\":[{\"name\":\"id\",\"type\":\"int\"},{\"name\":\"lat\",\"type\":\"string\"},{\"name\":\"lon\",\"type\":\"string\"},{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"country\",\"type\":\"string\"},{\"name\":\"population\",\"type\":\"long\"}]}")
}