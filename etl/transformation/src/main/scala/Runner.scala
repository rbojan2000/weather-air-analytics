import com.typesafe.scalalogging.LazyLogging
import config.AppConfig
import org.apache.spark.sql.SparkSession
import picocli.CommandLine
import picocli.CommandLine.{Command, Option}
import transformation.Transformation

import java.util.concurrent.Callable

@Command(
  name = "transformation",
  mixinStandardHelpOptions = true,
)
class Runner extends Callable[Int]
  with Transformation
  with LazyLogging {

  @Option(
    names = Array("-j", "--job"),
    description = Array(
      "Specifies which job should be run. Valid values are: " +
        "convert-to-delta, " +
        "convert-all-to-delta."
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
    required = true
  )
  private var startDate: String = ""

  @Option(
    names = Array("-ed", "--end-date"),
    description = Array("End date for data analysis."),
    required = true
  )
  private var endDate: String = ""

  @Option(
    names = Array("-dt", "--data-type"),
    description = Array("Type of data (e.g., air-quality, weather)."),
    required = true
  )
  private var dataType: String = ""

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