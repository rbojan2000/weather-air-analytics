package jobs

import com.typesafe.scalalogging.LazyLogging
import config.AppConfig
import io.delta.tables.DeltaTable
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.expressions.{Window, WindowSpec}
import org.apache.spark.sql.functions.{avg, col, max, min, rank, to_date}

import java.sql.Timestamp

trait Analytics extends LazyLogging {

  def windSpeedPollutantRatio(pollutant: String)
                             (implicit spark: SparkSession): DataFrame = {

    val weatherDF: DataFrame = DeltaTable.forPath(spark, AppConfig.deltaWeather).toDF
      .withColumn("date", to_date(col("date")))

    val dailyAirQuality: DataFrame = getDailyAirPollutantConcetrations()

    weatherDF.show()
    dailyAirQuality.show()
    val weatherAndAirQualityData: DataFrame = dailyAirQuality.join(weatherDF, Seq("city", "date"), "inner")

    weatherAndAirQualityData.show(10)

    val window: WindowSpec = Window
      .partitionBy(col("date"))
      .rowsBetween(-2, 0)

    val windSpeedPollutantRatioDF = weatherAndAirQualityData
      .withColumn("avg_wind_speed_3d",
        avg("wind_speed_10m").over(window))
      .withColumn(s"avg_${pollutant}_concetration",
        avg(s"avg($pollutant)").over(window))
      .withColumn("wind_speed_pollutant_ratio",
        col("avg_wind_speed_3d") / col(s"avg_${pollutant}_concetration"))

    windSpeedPollutantRatioDF
      .select("date", "city", "avg_wind_speed_3d", "wind_speed_10m", s"avg_${pollutant}_concetration", s"avg($pollutant)", "wind_speed_pollutant_ratio", "avg_wind_speed_3d")
      .show(300)

    windSpeedPollutantRatioDF
  }

  def rankPollutantConcentrationByHour(pollutant: String)
                                      (implicit spark: SparkSession): DataFrame = {

    val airQualityDF: DataFrame = DeltaTable.forPath(spark, AppConfig.deltaAirQuality).toDF

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
      .show(numRows = 300)

    hourlyPollutantConcentrationRank
  }

  def hourlyPollutantMaximumAndAverage(pollutant: String)
                                      (implicit spark: SparkSession): DataFrame = {

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
      .show(numRows = 300)

    hourlyMeasurmentDiffFromAvgAndMax
  }

  def correlationBetweenAirQualityAndWeather(airPollutant: String, weatherParam: String)
                                            (implicit spark: SparkSession): Double = {

    val weatherDF: DataFrame = DeltaTable.forPath(spark, AppConfig.deltaWeather).toDF
    val dailyAirQuality: DataFrame = getDailyAirPollutantConcetrations()

    val weatherAndAirQualityData: DataFrame = dailyAirQuality.join(weatherDF, Seq("city", "date"), "inner")

    val correlation: Double = weatherAndAirQualityData.stat.corr(s"avg($airPollutant)", weatherParam)

    logger.info(s"callculated correlation: {$correlation}")

    correlation
  }

  def findTop10CitiesByAvgPollutantSpecie(pollutant: String,
                                          startDate: Timestamp,
                                          endDate: Timestamp)
                                         (implicit spark: SparkSession): Unit = {
    val deltaTable = DeltaTable.forPath(spark, AppConfig.deltaAirQuality)

    deltaTable.toDF
      .filter(col("date").between(startDate, endDate))
      .groupBy(col("city"))
      .agg(avg(col(pollutant)).as("avgPoll"))
      .orderBy("avgPoll")
      .limit(10)
      .show(numRows = 300)
  }

  private def getDailyAirPollutantConcetrations()(implicit spark: SparkSession): DataFrame = {

    val airQualityDF = DeltaTable.forPath(spark, AppConfig.deltaAirQuality)
      .toDF
      .withColumn("date", to_date(col("date")))

    airQualityDF
      .groupBy("date", "city")
      .agg(
        avg("pm10"),
        avg("pm2_5"),
        avg("carbon_monoxide"),
        avg("nitrogen_dioxide"),
        avg("sulphur_dioxide"),
        avg("ozone"),
        avg("aerosol_optical_depth"),
        avg("dust"),
        avg("uv_index"),
        avg("uv_index_clear_sky"),
        avg("ammonia"),
        avg("alder_pollen"),
        avg("birch_pollen"),
        avg("grass_pollen"),
        avg("mugwort_pollen"),
        avg("olive_pollen"),
        avg("ragweed_pollen")
      )
  }
}
