package jobs

import com.typesafe.scalalogging.LazyLogging
import config.AppConfig
import io.delta.tables.DeltaTable
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{avg, col}

import java.sql.Timestamp

trait Analytics extends LazyLogging{
  def FindTop10CitiesByAvgPollutantSpecie(spark: SparkSession, pollutantSpecie: String, startDate: Timestamp, endDate: Timestamp): Unit = {
    val deltaTable = DeltaTable.forPath(spark, AppConfig.deltaAirQuality)

    deltaTable.toDF
      .filter(col("date").between(startDate, endDate))
      .groupBy(col("city"))
      .agg(avg(col(pollutantSpecie)).as("avgPoll"))
      .orderBy("avgPoll")
      .limit(10)
      .show()
  }
}

