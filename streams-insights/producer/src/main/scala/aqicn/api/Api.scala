package aqicn.api

import aqicn.domain.{AirQualityValue, City}
import sttp.client3.{HttpURLConnectionBackend, Identity, SttpBackend}

trait Api {
  val backend: SttpBackend[Identity, Any] = HttpURLConnectionBackend()

  def getAirQualityByCity(city: City): AirQualityValue
}
