package runner

import jobs.{ConvertAllToDelta, ConvertHistoricalDataToDelta, FindTop10CitiesByAvgPollutantSpecie}
import org.apache.spark.sql.SparkSession

import java.sql.Timestamp

object Runner {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("WeatherAirInsights")
      .config("spark.sql.extensions", "io.delta.sql.DeltaSparkSessionExtension")
      .config("spark.sql.catalog.spark_catalog", "org.apache.spark.sql.delta.catalog.DeltaCatalog")
      .getOrCreate()

    val jobToRun = args(0)

    jobToRun match {
      case "convertToDelta" => {
        val city = args(1)
        val dType = args(2)
        val startDate = args(3)
        val endDate = args(4)
        ConvertHistoricalDataToDelta.run(spark, city, dType, startDate, endDate)
      }
      case "convertAllToDelta" => {
        val dType = args(1)
        val startDate = args(2)
        val endDate = args(3)
        ConvertAllToDelta.run(spark, dType, startDate, endDate)
      }
      case "findTop10CitiesByAvgPollutantSpecie" => {
        val pollutantSpecie = args(1)
        val startDate = args(2)
        val endDate = args(3)

        FindTop10CitiesByAvgPollutantSpecie.run(spark, pollutantSpecie, Timestamp.valueOf(startDate), Timestamp.valueOf(endDate))
      }
    }
    spark.stop()
  }
}
