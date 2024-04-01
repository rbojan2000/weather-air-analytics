package transformation

import com.typesafe.scalalogging.LazyLogging
import common.Common
import config.AppConfig
import io.delta.tables.DeltaTable
import org.apache.spark.sql.functions.lit
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.types.StructType
import utils.Utils

trait Transformation extends Common
  with Utils
  with LazyLogging {

  def convertAllToDelta(dType: String,
                        startDate: String,
                        endDate: String)
                       (implicit spark: SparkSession): Unit = {
    val cities = spark
      .read
      .option("delimiter", ",")
      .option("header", "true")
      .csv(AppConfig.europeanCitiesCsvPath)

    cities.collect().foreach { row =>
      val cityName = row.getAs[String]("city")

      logger.info(s"Converting to delta dor city: ${cityName}")

      convertHistoricalDataToDelta(cityName, dType, startDate, endDate)
    }
  }

  def convertHistoricalDataToDelta(
                                    city: String,
                                    dType: String,
                                    startDate: String,
                                    endDate: String
                                  )(implicit spark: SparkSession): Unit = {

    val dataSchema: StructType = getSchema(dType)

    val csvPath = getBronzeTablePath(dType, startDate, endDate, city)
    val rawDF = readCsv(csvPath, dataSchema)

    val filteredDF = processRawDataFrame(rawDF, dataSchema, city)
    saveTableIfNotExist(filteredDF, dType)

    val deltaTable = DeltaTable.forPath(spark, getSilverTablePath(dType))

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

  def processRawDataFrame(rawDF: org.apache.spark.sql.DataFrame, schema: org.apache.spark.sql.types.StructType, city: String): DataFrame = {
    rawDF
      .withColumn("city", lit(city))
      .na.drop(
        "all",
        schema.fieldNames.filterNot(colName => colName == "id" || colName == "date")
      )
  }


  def getColumnMapping(schema: org.apache.spark.sql.types.StructType, alias: String): Map[String, String] = {
    schema
      .fieldNames
      .map(colName => colName -> s"$alias.$colName")
      .toMap
      .+("city" -> s"$alias.city")
  }
}
