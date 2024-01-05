package aqicn.decoder

import aqicn.domain.{AirQualityValue, City, Iaqi}
import com.typesafe.scalalogging.LazyLogging
import org.slf4j.MarkerFactory
import play.api.libs.json._

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

trait Decoder extends LazyLogging {

  def decodeAirQuality(encodedAirQuality: String, city: City): AirQualityValue = {
    try {
      val json = Json.parse(encodedAirQuality)

      val aqi = (json \ "data" \ "aqi").asOpt[Int]
      val idx = (json \ "data" \ "idx").as[Int]
      val dominentPol = (json \ "data" \ "dominentpol").as[String]

      val aqiData = parseIaqi(json \ "data" \ "iaqi")
      val (url, stationName) = parseAttributions(json \ "data")
      val stmTimestamp = parseTimestamp(json \ "data", city: City)

      AirQualityValue(url = url, stationName = stationName, dominentPol = dominentPol, aqi = aqi, index = idx, STM = stmTimestamp, iaqi = aqiData, city = city)
    } catch {
      case e: Exception =>
        logger.warn(MarkerFactory.getMarker("SPECIAL"), s"Error decoding air quality for city ${city.name}: ", e)
        throw e
    }
  }

  private def parseIaqi(json: JsLookup): Iaqi = {
    Iaqi(
      (json \ "co" \ "v").asOpt[Double],
      (json \ "h" \ "v").asOpt[Double],
      (json \ "no2" \ "v").asOpt[Double],
      (json \ "o3" \ "v").asOpt[Double],
      (json \ "p" \ "v").asOpt[Double],
      (json \ "pm10" \ "v").asOpt[Double],
      (json \ "pm25" \ "v").asOpt[Double],
      (json \ "so2" \ "v").asOpt[Double]
    )
  }

  private def parseAttributions(json: JsLookup): (String, String) = {
    val attributions = (json \ "attributions").as[Seq[JsObject]]
    attributions.headOption.map(obj =>
      ((obj \ "url").as[String], (obj \ "name").as[String])
    ).getOrElse(("", ""))
  }

  private def parseTimestamp(json: JsLookup, city: City): Option[Long] = {
    val timestampString: Option[String] = (json \ "time" \ "iso").asOpt[String]

    val epochMillis: Option[Long] = timestampString.flatMap { stm =>
      try {
        val offsetDateTime = OffsetDateTime.parse(stm, DateTimeFormatter.ISO_DATE_TIME)
        Some(offsetDateTime.toInstant.toEpochMilli)
      } catch {
        case _: Exception =>
          logger.warn(MarkerFactory.getMarker("SPECIAL"), s"Time not available for ${city.name}")
          None
      }
    }

    epochMillis
  }
}
