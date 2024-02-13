package aqicn

import aqicn.AvroSerdes._
import aqicn.avro._
import aqicn.config.Configuration
import aqicn.domain._
import com.sksamuel.avro4s.BinaryFormat
import com.sksamuel.avro4s.kafka.GenericSerde
import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.streams.Topology
import org.apache.kafka.streams.kstream.GlobalKTable
import org.apache.kafka.streams.scala.ImplicitConversions._
import org.apache.kafka.streams.scala._
import org.apache.kafka.streams.scala.kstream.{KStream, KTable, Materialized}
import org.apache.kafka.common.utils.Bytes
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsConfig.{APPLICATION_ID_CONFIG, BOOTSTRAP_SERVERS_CONFIG}
import org.apache.kafka.streams.scala.ImplicitConversions._
import org.apache.kafka.streams.scala._
import org.apache.kafka.streams.scala.kstream.{KGroupedStream, KTable, Materialized}
import org.apache.kafka.streams.state.KeyValueStore
case class TopologyBuilder() extends LazyLogging
  with CityAqiJoiner {

  val builder: StreamsBuilder = new StreamsBuilder()

  def build: Topology = {
    val aqicnStream: KStream[AirQualityId, AirQualityValue] = builder.stream[AirQualityId, AirQualityValue](Configuration.kafkaAirQualityTopic)
    val citiesTable: GlobalKTable[CityId, City] = builder.globalTable[CityId, City](Configuration.kafkaCitiesTopic)

    //    def globalTable[K, V](topic: _root_.scala.Predef.String)(implicit consumed: _root_.org.apache.kafka.streams.scala.kstream.Consumed[K, V]): _root_.org.apache.kafka.streams.kstream.GlobalKTable[K, V] = ???

    implicit val cityAqiInfoSerde: GenericSerde[CityAqiInfo] = new GenericSerde[CityAqiInfo](BinaryFormat)


    val carAndLocationData: KTable[CityId, CityAqiInfo] = aqicnStream
      .join[CityAqiInfo, CityId, City](
        citiesTable,
        keyExtractor = (carData: AirQualityValue) => carData.city.name,
        joiner = (carData: AirQualityValue, locationData: City) => CityAqiInfo(""),
        materialized = implicitly[Materialized[CityId, CityAqiInfo, KeyValueStore[Bytes, Array[Byte]]]]
      )


    aqicnStream.leftJoin(citiesTable)(joinCityWithAqi)


    builder.build()
  }
}
