package aqicn.topology

import aqicn.avro.AvroSerdes._
import aqicn.aggregate.CountryAqiKpiAggregator
import aqicn.config.Configuration
import aqicn.domain._
import aqicn.joiner.CityAqiJoiner
import aqicn.serdes.Serdes
import aqicn.domain.CountryAirQualityMetrics
import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.streams.Topology
import org.apache.kafka.streams.kstream.{GlobalKTable, Named, SessionWindows, Windowed}
import org.apache.kafka.streams.scala.ImplicitConversions._
import org.apache.kafka.streams.scala._
import org.apache.kafka.streams.scala.kstream._
import org.apache.kafka.streams.scala.serialization.Serdes._

import java.time.Duration

case class TopologyBuilder() extends Serdes
  with CityAqiJoiner
  with CountryAqiKpiAggregator
  with LazyLogging {

  val builder: StreamsBuilder = new StreamsBuilder()

  def build: Topology = {
    val aqicnStream: KStream[AirQualityId, AirQualityValue] = builder.stream[AirQualityId, AirQualityValue](Configuration.kafkaAirQualityTopic)
    val citiesGlobalTable: GlobalKTable[CityId, City] = builder.globalTable[CityId, City](Configuration.kafkaCitiesTopic)

    val cityWithAqiStream: KStream[AirQualityId, CityAqiInfo] =
      aqicnStream
        .leftJoin(citiesGlobalTable)(
          (aqiId: AirQualityId, _) => CityId(aqiId.city),
          joinCityWithAqi
        )

    val groupedByCountry: KGroupedStream[String, CityAqiInfo] =
      cityWithAqiStream
        .groupBy((_, value) => value.country)(Grouped.`with`(stringSerde, cityAqiInfoSerde))

    val valued: KTable[Windowed[String], CountryAirQualityMetrics] = groupedByCountry
      .windowedBy(SessionWindows.ofInactivityGapWithNoGrace(Duration.ofSeconds(5)))
      .aggregate(
        initializer = CountryAirQualityMetrics(
          country = ???, cityWithHighestPM10 = ???, cityWithHighestPM25 = ???, cityWithHighestAqi = ???, aqiValue = ???, dominantPollutant = ???, dominantPollutantInCountry = ???, averageCountryAqi = ???
        )
      )(aggregate, merge)(Materialized.`with`(stringSerde, countryAqiInfoKpiSerde))

    valued.toStream(Named.as("processed-country-aqi-kpi"))
      .peek((k, v) =>
          logger.debug(s"Enriched player game {key: $k} with KPI: $v")
      )
    builder.build()
  }
}
