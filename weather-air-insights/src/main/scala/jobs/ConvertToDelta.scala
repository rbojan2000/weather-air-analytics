package jobs

import com.typesafe.scalalogging.LazyLogging
import config.AppConfig
import io.delta.tables.DeltaTable
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.StructType
import utils.DeltaConversion

trait ConvertToDelta extends DeltaConversion with LazyLogging {

  def ConvertAllToDelta(dType: String,
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

      ConvertHistoricalDataToDelta(cityName, dType, startDate, endDate)
    }
  }

  def ConvertHistoricalDataToDelta(
                                    city: String,
                                    dType: String,
                                    startDate: String,
                                    endDate: String
                                  )(implicit spark: SparkSession): Unit = {

    val dataSchema: StructType = getSchema(dType)

    val csvPath = s"${AppConfig.csvBasePath}$dType/${city}_${startDate}_$endDate.csv"
    val rawDF = readCsv(csvPath, dataSchema)

    val filteredDF = processRawDataFrame(rawDF, dataSchema, city)
    saveTableIfNotExist(filteredDF, dType)

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
}
