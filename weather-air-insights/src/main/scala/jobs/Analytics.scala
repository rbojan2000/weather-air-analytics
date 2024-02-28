package jobs

import com.typesafe.scalalogging.LazyLogging
import config.AppConfig
import io.delta.tables.DeltaTable
import org.apache.spark.sql.{DataFrame, SparkSession, functions}
import org.apache.spark.sql.expressions.{Window, WindowSpec}
import org.apache.spark.sql.functions.{avg, col, min, max, rank, to_date, to_timestamp}

import java.sql.Timestamp

trait Analytics extends LazyLogging {

  def rankPollutantConcentrationByHour(pollutant: String)
                                      (implicit spark: SparkSession,
                                       showRowNum: Int): DataFrame = {

    val airQualityDF = DeltaTable.forPath(spark, AppConfig.deltaAirQuality).toDF

    val hourlyWindow: WindowSpec = Window
      .partitionBy("date")
      .orderBy(col(pollutant).desc)
      .rowsBetween(Window.unboundedPreceding, Window.currentRow)

    val hourlyPollutantConcentrationRank: DataFrame = airQualityDF
      .withColumn(s"hourlyMax${pollutant.capitalize}", max(col(pollutant))
        .over(hourlyWindow))
      .withColumn(s"hourlyMin${pollutant.capitalize}", min(col(pollutant))
        .over(hourlyWindow))
      .withColumn("rank", rank().over(hourlyWindow))

    hourlyPollutantConcentrationRank
      .select("date", "city", pollutant, s"hourlyMax${pollutant.capitalize}", s"hourlyMin${pollutant.capitalize}", "rank")
      .show(numRows = showRowNum)

    hourlyPollutantConcentrationRank
  }

  def hourlyPollutantMaximumAndAverage(pollutant: String)
                                      (implicit spark: SparkSession,
                                       showRowNum: Int): DataFrame = {

    val airQualityDF = DeltaTable.forPath(spark, AppConfig.deltaAirQuality).toDF

    val hourlyWindow: WindowSpec = Window
      .partitionBy("date")
      .orderBy("date")
      .rowsBetween(Window.unboundedPreceding, Window.unboundedFollowing)

    val hourlyMaxPollutant: DataFrame = airQualityDF
      .withColumn(s"hourly_max_${pollutant}", max(col(pollutant))
        .over(hourlyWindow))

    val hourlyMaxAvgPollutant: DataFrame = hourlyMaxPollutant
      .withColumn(s"hourly_avg_${pollutant}", avg(col(pollutant))
        .over(hourlyWindow))


    val hourlyMeasurmentDiffFromAvgAndMax: DataFrame = hourlyMaxAvgPollutant
      .withColumn(s"current_dif_${pollutant}_from_avg", col(s"hourly_avg_${pollutant}") - col(pollutant))
      .withColumn(s"current_dif_${pollutant}_from_max", col(s"hourly_max_${pollutant}") - col(pollutant))

    hourlyMeasurmentDiffFromAvgAndMax
      .select("date", "city", pollutant, s"hourly_max_${pollutant}", s"hourly_avg_${pollutant}", s"current_dif_${pollutant}_from_avg", s"current_dif_${pollutant}_from_max")
      .show(numRows = showRowNum)


    hourlyMeasurmentDiffFromAvgAndMax
  }

  def findTop10CitiesByAvgPollutantSpecie(pollutant: String,
                                          startDate: Timestamp,
                                          endDate: Timestamp)
                                         (implicit spark: SparkSession,
                                          showRowNum: Int): Unit = {
    val deltaTable = DeltaTable.forPath(spark, AppConfig.deltaAirQuality)

    deltaTable.toDF
      .filter(col("date").between(startDate, endDate))
      .groupBy(col("city"))
      .agg(avg(col(pollutant)).as("avgPoll"))
      .orderBy("avgPoll")
      .limit(10)
      .show(numRows = showRowNum)
  }
}
