package utils

import com.typesafe.scalalogging.LazyLogging
import config.AppConfig
import io.delta.tables.DeltaTable
import org.apache.spark.sql.functions.lit
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

trait DeltaConversion extends LazyLogging {
  def saveTableIfNotExist(data: DataFrame, dType: String)
                         (implicit spark: SparkSession): Unit = {
    if (!DeltaTable.isDeltaTable(spark, getDeltaTablePath(dType))) {
      data
        .write
        .format("delta")
        .mode(SaveMode.Overwrite)
        .option("mergeSchema", "true")
        .save(getDeltaTablePath(dType))
      return
    }
  }

  def getDeltaTablePath(dType: String): String = {
    if (dType.contains("air")) {
      AppConfig.deltaAirQuality
    } else {
      AppConfig.deltaWeather
    }
  }

  def readCsv(path: String,
              schema: org.apache.spark.sql.types.StructType)
             (implicit spark: SparkSession): DataFrame = {
    spark.read
      .option("delimiter", ",")
      .option("header", "true")
      .schema(schema)
      .csv(path)
  }

  def processRawDataFrame(rawDF: org.apache.spark.sql.DataFrame, schema: org.apache.spark.sql.types.StructType, city: String): DataFrame = {
    rawDF
      .withColumn("city", lit(city))
      .na.drop(
        "all",
        schema.fieldNames.filterNot(colName => colName == "id" || colName == "date")
      )
  }

  def getSchema(dType: String): StructType = {
    if (dType.contains("air")) {
      schema.AirQuality.schema
    } else {
      schema.Weather.schema
    }
  }

  def getColumnMapping(schema: org.apache.spark.sql.types.StructType, alias: String): Map[String, String] = {
    schema
      .fieldNames
      .map(colName => colName -> s"$alias.$colName")
      .toMap
      .+("city" -> s"$alias.city")
  }

}
