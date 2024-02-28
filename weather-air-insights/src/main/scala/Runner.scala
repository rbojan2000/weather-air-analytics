import com.typesafe.scalalogging.LazyLogging
import config.AppConfig
import jobs.{Analytics, ConvertToDelta}
import org.apache.spark.sql.SparkSession
import picocli.CommandLine
import picocli.CommandLine.{Command, Option}

import java.util.concurrent.Callable
import java.sql.Timestamp

@Command(
  name = "weather-air-insights",
  mixinStandardHelpOptions = true,
  version = Array("1.0"),
  description = Array("Run weather and air quality analytics jobs.")
)
class Runner extends Callable[Int]
  with LazyLogging
  with ConvertToDelta
  with Analytics {

  @Option(
    names = Array("-j", "--job"),
    description = Array(
      "Specifies which job should be run. Valid values are: " +
        "convert-to-delta, " +
        "convert-all-to-delta, " +
        "find-top10-cities-by-avg-pollutant-specie."
    ),
    required = true
  )
  private var job: String = ""

  @Option(
    names = Array("-c", "--city"),
    description = Array("City name."),
    required = false
  )
  private var city: String = ""

  @Option(
    names = Array("-sd", "--start-date"),
    description = Array("Start date for data analysis."),
    required = false
  )
  private var startDate: String = ""

  @Option(
    names = Array("-ed", "--end-date"),
    description = Array("End date for data analysis."),
    required = false
  )
  private var endDate: String = ""

  @Option(
    names = Array("-dt", "--data-type"),
    description = Array("Type of data (e.g., air-quality, weather)."),
    required = false
  )
  private var dataType: String = ""

  @Option(
    names = Array("-p", "--pollutant"),
    description = Array("Name of the pollutant."),
    required = false
  )
  private var pollutant: String = ""

  @Option(
    names = Array("-wp", "--weather-param"),
    description = Array("Name of the weather param."),
    required = false
  )
  private var weatherParam: String = ""

  override def call(): Int = {
    run()
    0
  }

  private def run(): Unit = {
    implicit val showRowNum: Integer = AppConfig.showRowNum

    implicit val spark: SparkSession = SparkSession
      .builder()
      .config("spark.sql.extensions", "io.delta.sql.DeltaSparkSessionExtension")
      .config("spark.sql.catalog.spark_catalog", "org.apache.spark.sql.delta.catalog.DeltaCatalog")
      .getOrCreate()

    job match {
      case "convert-to-delta" =>
        convertHistoricalDataToDelta(
          city,
          dataType,
          startDate,
          endDate
        )

      case "convert-all-to-delta" =>
        convertAllToDelta(
          dataType,
          startDate,
          endDate
        )

      case "find-top10-cities-by-avg-pollutant-specie" =>
        findTop10CitiesByAvgPollutantSpecie(
          pollutant,
          Timestamp.valueOf(startDate),
          Timestamp.valueOf(endDate)
        )

      case "hourly-pollutant-metrics" =>
        hourlyPollutantMaximumAndAverage(pollutant)

      case "hourly-pollutant-cities-rank-by-max-pollutant-concetration" =>
        rankPollutantConcentrationByHour(pollutant)

      case "correlation-between-air-quality-and-weather" =>
        correlationBetweenAirQualityAndWeather(pollutant, weatherParam)

      case _ =>
        println("Invalid job option.")
    }
    spark.stop()
  }
}

object Runner extends App {
  val exitCode = new CommandLine(new Runner()).execute(args: _*)
  System.exit(exitCode)
}