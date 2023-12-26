package schema

import org.apache.spark.sql.types._

object Weather {
  val schema: StructType = StructType(
    Array(
      StructField("date", TimestampType, nullable = true),
      StructField("weather_code", DoubleType, nullable = true),
      StructField("temperature_2m_max", DoubleType, nullable = true),
      StructField("temperature_2m_min", DoubleType, nullable = true),
      StructField("temperature_2m_mean", DoubleType, nullable = true),
      StructField("apparent_temperature_max", DoubleType, nullable = true),
      StructField("apparent_temperature_min", DoubleType, nullable = true),
      StructField("apparent_temperature_mean", DoubleType, nullable = true),
      StructField("sunrise", IntegerType, nullable = true),
      StructField("sunset", IntegerType, nullable = true),
      StructField("daylight_duration", DoubleType, nullable = true),
      StructField("sunshine_duration", DoubleType, nullable = true),
      StructField("precipitation_sum", DoubleType, nullable = true),
      StructField("rain_sum", DoubleType, nullable = true),
      StructField("snowfall_sum", DoubleType, nullable = true),
      StructField("precipitation_hours", DoubleType, nullable = true),
      StructField("wind_speed_10m_max", DoubleType, nullable = true),
      StructField("wind_gusts_10m_max", DoubleType, nullable = true),
      StructField("wind_direction_10m_dominant", DoubleType, nullable = true),
      StructField("shortwave_radiation_sum", DoubleType, nullable = true)
    )
  )
}
