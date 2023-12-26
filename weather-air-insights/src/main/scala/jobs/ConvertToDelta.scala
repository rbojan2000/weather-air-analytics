package jobs

import config.AppConfig
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{SaveMode, SparkSession}

object ConvertToDelta {
  def run(spark: SparkSession, city: String, dType: String, startDate: String, endDate: String): Unit = {
    val selectedSchema: StructType = if (dType.contains("air")) {
      schema.AirQuality.schema
    } else {
      schema.Weather.schema
    }

    val csvPath = s"${AppConfig.csvBasePath}$dType/${city}_${startDate}_$endDate.csv"
    val rawDF = spark
      .read
      .option("delimiter", ",")
      .option("header", "true")
      .schema(selectedSchema)
      .csv(csvPath)

    if (rawDF.isEmpty) {
      throw new RuntimeException(s"CSV file at $csvPath does not exist.")
    }
    val filteredDF = rawDF.na.drop(
      "all",
      selectedSchema
        .fieldNames
        .filterNot(colName => colName == "id" || colName == "date")
    )

    val deltaPath = s"${AppConfig.deltaBasePath}$dType/$city"
    filteredDF
      .write
      .format("delta")
      .mode(SaveMode.Overwrite)
      .option("mergeSchema", "true")
      .save(deltaPath)
  }
}

object ConvertAllToDelta {
  def run(spark: SparkSession, dType: String, startDate: String, endDate: String): Unit = {
    val cities = spark
      .read
      .option("delimiter", ",")
      .option("header", "true")
      .csv(AppConfig.europeanCitiesCsvPath)

    cities.collect().foreach { row =>
      val cityName = row.getAs[String]("city")
      ConvertToDelta.run(spark, cityName, dType, startDate, endDate)
    }
  }
}
