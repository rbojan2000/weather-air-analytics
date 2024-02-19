package jobs

import config.AppConfig
import io.delta.tables.DeltaTable
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.StructType
import utils.DeltaConversionUtils

trait ConvertToDelta extends DeltaConversionUtils{

  def ConvertHistoricalDataToDelta(
           spark: SparkSession,
           city: String,
           dType: String,
           startDate: String,
           endDate: String
         ): Unit = {

    val dataSchema: StructType = getSchema(dType)

    val csvPath = s"${AppConfig.csvBasePath}$dType/${city}_${startDate}_$endDate.csv"
    val rawDF = readCsv(spark, csvPath, dataSchema)

    val filteredDF = processRawDataFrame(rawDF, dataSchema, city)
    saveTableIfNotExist(spark, filteredDF, dType)

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

  def ConvertAllToDelta(spark: SparkSession, dType: String, startDate: String, endDate: String): Unit = {
    val cities = spark
      .read
      .option("delimiter", ",")
      .option("header", "true")
      .csv(AppConfig.europeanCitiesCsvPath)

    cities.collect().foreach { row =>
      val cityName = row.getAs[String]("city")

      ConvertHistoricalDataToDelta(spark, cityName, dType, startDate, endDate)
    }
  }
}
