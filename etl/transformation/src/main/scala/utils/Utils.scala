package utils

import com.typesafe.scalalogging.LazyLogging
import config.AppConfig
import org.apache.spark.sql.types.StructType

trait Utils extends LazyLogging {

  def getBronzeTablePath(dType: String, startDate: String, endDate: String, city: String): String = {
    if (dType.contains("air")) {
      s"${AppConfig.bronzeHistoricalAirQuality}/${city}_${startDate}_$endDate.csv"
    } else {
      s"${AppConfig.bronzeHistoricalWeatherDaily}/${city}_${startDate}_$endDate.csv"
    }
  }

  def getSilverTablePath(dType: String): String = {
    if (dType.contains("air")) {
      AppConfig.silverHistoricalAirQuality
    } else {
      AppConfig.silverHistoricalWeatherDaily
    }
  }

  def getSchema(dType: String): StructType = {
    if (dType.contains("air")) {
      schema.AirQuality.schema
    } else {
      schema.Weather.schema
    }
  }

}
