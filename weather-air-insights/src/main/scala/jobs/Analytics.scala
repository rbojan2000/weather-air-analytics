package jobs

import com.typesafe.scalalogging.LazyLogging
import config.AppConfig
import io.delta.tables.DeltaTable
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions.{avg, col, to_date, to_timestamp}

import java.sql.Timestamp

trait Analytics extends LazyLogging {
  def FindTop10CitiesByAvgPollutantSpecie(pollutantSpecie: String,
                                          startDate: Timestamp,
                                          endDate: Timestamp)
                                         (implicit spark: SparkSession): Unit = {
    val deltaTable = DeltaTable.forPath(spark, AppConfig.deltaAirQuality)

    deltaTable.toDF
      .filter(col("date").between(startDate, endDate))
      .groupBy(col("city"))
      .agg(avg(col(pollutantSpecie)).as("avgPoll"))
      .orderBy("avgPoll")
      .limit(10)
      .show()
  }

  def calculateDailyAveragePollution(pollutant: String)
                                    (implicit spark: SparkSession): DataFrame = {
    val airQualityDF = DeltaTable.forPath(spark, AppConfig.deltaAirQuality).toDF
    val airQualityData = airQualityDF.withColumn("date", to_timestamp(col("date"), "yyyy-MM-dd HH:mm:ss"))

    val windowSpec = Window.partitionBy(to_date(col("date")))
    val avgPM10PerDay = avg(col(pollutant)).over(windowSpec)
    val resultDF = airQualityData.withColumn("avg_pm10_per_day", avgPM10PerDay)

    resultDF.show()

    resultDF
  }
}
