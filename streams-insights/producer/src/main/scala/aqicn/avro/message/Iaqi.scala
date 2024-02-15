/** MACHINE-GENERATED FROM AVRO SCHEMA. DO NOT EDIT DIRECTLY */
package aqicn.avro.message

import scala.annotation.switch

final case class Iaqi(var co: Option[Double] = None, var h: Option[Double] = None, var no2: Option[Double] = None, var o3: Option[Double] = None, var p: Option[Double] = None, var pm10: Option[Double] = None, var pm25: Option[Double] = None, var so2: Option[Double] = None) extends org.apache.avro.specific.SpecificRecordBase {
  def this() = this(None, None, None, None, None, None, None, None)
  def get(field$: Int): AnyRef = {
    (field$: @switch) match {
      case 0 => {
        co match {
          case Some(x) => x
          case None => null
        }
      }.asInstanceOf[AnyRef]
      case 1 => {
        h match {
          case Some(x) => x
          case None => null
        }
      }.asInstanceOf[AnyRef]
      case 2 => {
        no2 match {
          case Some(x) => x
          case None => null
        }
      }.asInstanceOf[AnyRef]
      case 3 => {
        o3 match {
          case Some(x) => x
          case None => null
        }
      }.asInstanceOf[AnyRef]
      case 4 => {
        p match {
          case Some(x) => x
          case None => null
        }
      }.asInstanceOf[AnyRef]
      case 5 => {
        pm10 match {
          case Some(x) => x
          case None => null
        }
      }.asInstanceOf[AnyRef]
      case 6 => {
        pm25 match {
          case Some(x) => x
          case None => null
        }
      }.asInstanceOf[AnyRef]
      case 7 => {
        so2 match {
          case Some(x) => x
          case None => null
        }
      }.asInstanceOf[AnyRef]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
  }
  def put(field$: Int, value: Any): Unit = {
    (field$: @switch) match {
      case 0 => this.co = {
        value match {
          case null => None
          case _ => Some(value)
        }
      }.asInstanceOf[Option[Double]]
      case 1 => this.h = {
        value match {
          case null => None
          case _ => Some(value)
        }
      }.asInstanceOf[Option[Double]]
      case 2 => this.no2 = {
        value match {
          case null => None
          case _ => Some(value)
        }
      }.asInstanceOf[Option[Double]]
      case 3 => this.o3 = {
        value match {
          case null => None
          case _ => Some(value)
        }
      }.asInstanceOf[Option[Double]]
      case 4 => this.p = {
        value match {
          case null => None
          case _ => Some(value)
        }
      }.asInstanceOf[Option[Double]]
      case 5 => this.pm10 = {
        value match {
          case null => None
          case _ => Some(value)
        }
      }.asInstanceOf[Option[Double]]
      case 6 => this.pm25 = {
        value match {
          case null => None
          case _ => Some(value)
        }
      }.asInstanceOf[Option[Double]]
      case 7 => this.so2 = {
        value match {
          case null => None
          case _ => Some(value)
        }
      }.asInstanceOf[Option[Double]]
      case _ => new org.apache.avro.AvroRuntimeException("Bad index")
    }
    ()
  }
  def getSchema: org.apache.avro.Schema = aqicn.avro.message.Iaqi.SCHEMA$
}

object Iaqi {
  val SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Iaqi\",\"namespace\":\"aqicn.avro.message\",\"fields\":[{\"name\":\"co\",\"type\":[\"null\",\"double\"],\"default\":null},{\"name\":\"h\",\"type\":[\"null\",\"double\"],\"default\":null},{\"name\":\"no2\",\"type\":[\"null\",\"double\"],\"default\":null},{\"name\":\"o3\",\"type\":[\"null\",\"double\"],\"default\":null},{\"name\":\"p\",\"type\":[\"null\",\"double\"],\"default\":null},{\"name\":\"pm10\",\"type\":[\"null\",\"double\"],\"default\":null},{\"name\":\"pm25\",\"type\":[\"null\",\"double\"],\"default\":null},{\"name\":\"so2\",\"type\":[\"null\",\"double\"],\"default\":null}]}")
}