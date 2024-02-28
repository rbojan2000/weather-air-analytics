package schema

import org.apache.spark.sql.types._

object AirQuality {
  val schema: StructType = StructType(
    Array(
      StructField("id", IntegerType, nullable = true),
      StructField("date", TimestampType, nullable = true),
      StructField("pm10", DoubleType, nullable = true),
      StructField("pm2_5", DoubleType, nullable = true),
      StructField("carbon_monoxide", DoubleType, nullable = true),
      StructField("nitrogen_dioxide", DoubleType, nullable = true),
      StructField("sulphur_dioxide", DoubleType, nullable = true),
      StructField("ozone", DoubleType, nullable = true),
      StructField("aerosol_optical_depth", DoubleType, nullable = true),
      StructField("dust", DoubleType, nullable = true),
      StructField("uv_index", DoubleType, nullable = true),
      StructField("uv_index_clear_sky", DoubleType, nullable = true),
      StructField("ammonia", DoubleType, nullable = true),
      StructField("alder_pollen", DoubleType, nullable = true),
      StructField("birch_pollen", DoubleType, nullable = true),
      StructField("grass_pollen", DoubleType, nullable = true),
      StructField("mugwort_pollen", DoubleType, nullable = true),
      StructField("olive_pollen", DoubleType, nullable = true),
      StructField("ragweed_pollen", DoubleType, nullable = true)
    )
  )
}
