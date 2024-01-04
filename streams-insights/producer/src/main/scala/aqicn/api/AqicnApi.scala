package aqicn.api

import aqicn.config.Configuration
import aqicn.decoder.Decoder
import aqicn.domain
import aqicn.domain.City
import com.typesafe.scalalogging.LazyLogging
import sttp.client3.{HttpURLConnectionBackend, UriContext, basicRequest}

case class AqicnApi(apiKey: String) extends Api with LazyLogging with Decoder{

  override def getAirQualityByCity(city: City): domain.AirQualityValue = {
    val backend = HttpURLConnectionBackend()
    val response = basicRequest
      .get(uri"${Configuration.aqicnRoot}/geo:${city.lat};${city.lon}/?token=$apiKey")
      .send(backend)

    response.body match {
      case Right(value) => decodeAirQuality(value, city)
      case Left(e) =>
        throw new RuntimeException(s"Players not available. Exception: $e")
    }
  }
}
