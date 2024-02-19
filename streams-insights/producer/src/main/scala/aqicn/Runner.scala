package aqicn

import aqicn.api.{Api, AqicnApi}
import aqicn.avro.message.City
import aqicn.config.Configuration
import aqicn.producer.{AqiProducer, CitiesProducer}
import aqicn.utils.CsvReader
import picocli.CommandLine
import picocli.CommandLine.{Command, Option}

import java.nio.file.Paths
import java.util.concurrent.Callable

@Command(
  name = "producer",
  mixinStandardHelpOptions = true,
  version = Array("1.0"),
  description = Array("Produce aqicn or cities data.")
)
class Runner extends Callable[Int] {

  @Option(
    names = Array("-d", "--dataset"),
    description = Array(
      "Specifies which dataset should be produced. Valid values are:[" +
        "cities," +
        "air-quality" +
        "]."
    )
  )
  private var dataset = ""

  override def call(): Int = {
    run()
    0
  }

  private def run(): Unit = {
    val api: Api = AqicnApi(Configuration.aqicnApiKey)
    implicit val cities: Seq[City] = CsvReader.readCities(filePath = Configuration.europeanCitiesPath)

    dataset match {
      case "cities" => CitiesProducer().produce()
      case "air-quality" => AqiProducer(api).produce()
      case _ => CitiesProducer().produce()
    }
  }
}

object Runner extends App {
  val exitCode = new CommandLine(new Runner()).execute(args: _*)
  System.exit(exitCode)
}
