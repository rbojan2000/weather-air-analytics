package aqicn.api

import aqicn.avro.message.{AirQuality, City}
import sttp.client3.{HttpURLConnectionBackend, Identity, SttpBackend}

trait Api {
  val backend: SttpBackend[Identity, Any] = HttpURLConnectionBackend()

  def getAirQualityByCity(city: City): AirQuality
}
