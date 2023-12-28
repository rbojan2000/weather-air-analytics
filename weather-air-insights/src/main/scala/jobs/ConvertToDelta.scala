package jobs

import config.AppConfig
import io.delta.tables.DeltaTable
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.functions.lit
import org.apache.spark.sql.types.StructType

object ConvertHistoricalDataToDelta {
  def run(
           spark: SparkSession,
           city: String,
           dType: String,
           startDate: String,
           endDate: String
         ): Unit = {

    val dataSchema: StructType = getSchema(dType)

    val csvPath = s"${AppConfig.csvBasePath}$dType/${city}_${startDate}_$endDate.csv"
    val rawDF = readCsv(spark, csvPath, dataSchema)

    if (rawDF.isEmpty) {
      throw new RuntimeException(s"CSV file at $csvPath does not exist.")
    }

    val filteredDF = processRawDataFrame(rawDF, dataSchema, city)

    val deltaTable = DeltaTable.forPath(spark, getDeltaTablePath(dType))

    deltaTable
      .as("table")
      .merge(
        filteredDF.as("updates"),
        s"table.city = updates.city AND table.date = updates.date"
      )
      .whenNotMatched
      .insertExpr(
        getColumnMapping(dataSchema, "updates")
      )
      .execute()
  }

  private def readCsv(spark: SparkSession, path: String, schema: org.apache.spark.sql.types.StructType) = {
    spark.read
      .option("delimiter", ",")
      .option("header", "true")
      .schema(schema)
      .csv(path)
  }

  private def processRawDataFrame(rawDF: org.apache.spark.sql.DataFrame, schema: org.apache.spark.sql.types.StructType, city: String) = {
    rawDF
      .withColumn("city", lit(city))
      .na.drop(
        "all",
        schema.fieldNames.filterNot(colName => colName == "id" || colName == "date")
      )
  }

  private def getDeltaTablePath(dType: String): String = {
    if (dType.contains("air")) {
      AppConfig.deltaAirQuality
    } else {
      AppConfig.deltaWeather
    }
  }

  private def getSchema(dType: String): StructType = {
    if (dType.contains("air")) {
      schema.AirQuality.schema
    } else {
      schema.Weather.schema
    }
  }

  private def getColumnMapping(schema: org.apache.spark.sql.types.StructType, alias: String): Map[String, String] = {
    val stringToString = schema
      .fieldNames
      .map(colName => colName -> s"$alias.$colName")
      .toMap
      .+("city" -> s"$alias.city")

    println(stringToString)
    stringToString
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

      ConvertHistoricalDataToDelta.run(spark, cityName, dType, startDate, endDate)
    }
  }
}