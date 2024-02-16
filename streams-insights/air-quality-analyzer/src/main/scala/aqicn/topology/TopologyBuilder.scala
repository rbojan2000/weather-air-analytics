package aqicn.topology

import aqicn.aggregate.CountryAirQualityMetricsAggregator
import aqicn.avro.message.{AirQuality, AirQualityWithPollutionLevel, City, CityAqiInfo, CityMetric, CountryAirQualityMetrics}
import aqicn.config.{AirPollutantLevelLimits, Configuration}
import aqicn.joiner.AqiCityJoiner
import aqicn.mapper.AirQualityPollutantLevelMapper
import aqicn.serdes.Serdes
import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.streams.Topology
import org.apache.kafka.streams.scala.ImplicitConversions._
import org.apache.kafka.streams.scala.StreamsBuilder
import org.apache.kafka.streams.scala.kstream._
import org.apache.kafka.streams.scala.serialization.Serdes._
import org.apache.kafka.streams.kstream.{GlobalKTable, Named, SlidingWindows, Windowed}

import java.time.Duration

case class TopologyBuilder() extends Serdes
  with AqiCityJoiner
  with LazyLogging
  with AirQualityPollutantLevelMapper
  with CountryAirQualityMetricsAggregator {

  val builder: StreamsBuilder = new StreamsBuilder()
  implicit val airPollutantLevelLimits: AirPollutantLevelLimits = AirPollutantLevelLimits()

  def build: Topology = {
    val airQualityStream: KStream[String, AirQuality] = builder.stream[String, AirQuality](Configuration.airQualityTopic)
    val citiesGlobalTable: GlobalKTable[String, City] = builder.globalTable[String, City](Configuration.citiesTopic)

    val cityWithAirPollutantLevelStream: KStream[String, AirQualityWithPollutionLevel] = airQualityStream
      .filter((_, v) => v.aqi.isDefined)
      .mapValues(
        mapToCityAirPollutantLevel
      )

    cityWithAirPollutantLevelStream
      .to(Configuration.cityairpollutantTopic)

    val airQualityWithCityStream: KStream[String, CityAqiInfo] = cityWithAirPollutantLevelStream
      .leftJoin(citiesGlobalTable)(
        (key, _) => key,
        joinAqiWithCity
      )
      .peek((k, v) =>
        logger.whenDebugEnabled {
          logger.debug(s"Enriched air quality {city: $k} with: $v")
        }
      )

    val airQualityWithCityGroupedStream: KGroupedStream[String, CityAqiInfo] =
      airQualityWithCityStream
        .groupBy((_, value) => value.country)(Grouped.`with`(stringSerde, cityAqiInfoSerde))

    val windowedCountryAirQualityMetrics: KTable[Windowed[String], CountryAirQualityMetrics] = airQualityWithCityGroupedStream
      .windowedBy(SlidingWindows.ofTimeDifferenceWithNoGrace(Duration.ofMinutes(Configuration.windowDuration)))
      .aggregate(
        initializer = CountryAirQualityMetrics("", CityMetric("", 1, "", ""), CityMetric("", 1, "", ""), CityMetric("", 1, "", ""), "", Map.empty, 0, 0)
      )(
        aggregate
      )(Materialized.`with`(stringSerde, countryAirQualityMetricsSerde))


    windowedCountryAirQualityMetrics
      .toStream(Named.as("processed-country-air-quality-metrics"))
      .selectKey((window, value) => value.country)
      .peek((k, v) =>
        logger.whenDebugEnabled {
          logger.debug(s"Country metrics {window: $k} metric: $v")
        })
      .to(Configuration.countryAqiMetricsTopic)

    builder.build()
  }
}
