package aqicn.decoder

import aqicn.domain.{AirQualityValue, City, Iaqi}
import com.typesafe.scalalogging.LazyLogging
import play.api.libs.json._

import java.time.OffsetDateTime

trait Decoder extends LazyLogging {

  def decodeAirQuality(encodedAirQuality: String, city: City): AirQualityValue = {
    try {
      logger.info(encodedAirQuality)

      val json = Json.parse(encodedAirQuality)

      val aqi = (json \ "data" \ "aqi").as[Int]
      val idx = (json \ "data" \ "idx").as[Int]
      val dominentPol = (json \ "data" \ "dominentpol").as[String]

      val aqiData = parseIaqi(json \ "data" \ "iaqi")
      val (url, stationName) = parseAttributions(json \ "data")
      val stmTimestamp = parseTimestamp(json \ "data")

      AirQualityValue(url = url, stationName = stationName, dominentPol = dominentPol, aqi = aqi, index = idx, STM = stmTimestamp, iaqi = aqiData, city = city)
    } catch {
      case e: Exception =>
        logger.error("Error decoding air quality", e)
        throw e
    }
  }

  private def parseIaqi(json: JsLookup): Iaqi = {
    Iaqi(
      (json \ "co" \ "v").as[Double],
      (json \ "h" \ "v").as[Double],
      (json \ "no2" \ "v").as[Double],
      (json \ "o3" \ "v").as[Double],
      (json \ "p" \ "v").as[Double],
      (json \ "pm10" \ "v").as[Double],
      (json \ "pm25" \ "v").as[Double],
      (json \ "so2" \ "v").as[Double]
    )
  }

  private def parseAttributions(json: JsLookup): (String, String) = {
    val attributions = (json \ "attributions").as[Seq[JsObject]]
    attributions.headOption.map(obj =>
      ((obj \ "url").as[String], (obj \ "name").as[String])
    ).getOrElse(("", ""))
  }

  private def parseTimestamp(json: JsLookup): Long = {
    val stm = (json \ "time" \ "iso").as[String]
    val offsetDateTime = OffsetDateTime.parse(stm)
    offsetDateTime.toInstant.toEpochMilli
  }
}
