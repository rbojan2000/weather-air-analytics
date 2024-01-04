package aqicn.producer

import aqicn.api.{Api, AqicnApi}
import aqicn.avro._
import aqicn.config.Configuration
import cats.effect.{ExitCode, IO, IOApp, Resource}
import cats.implicits._
import com.sksamuel.avro4s.RecordFormat
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG
import io.confluent.kafka.serializers.KafkaAvroSerializer
import org.apache.avro.generic.IndexedRecord
import org.apache.kafka.clients.producer.ProducerConfig.{ACKS_CONFIG, BOOTSTRAP_SERVERS_CONFIG, CLIENT_ID_CONFIG, KEY_SERIALIZER_CLASS_CONFIG, VALUE_SERIALIZER_CLASS_CONFIG}
import org.apache.kafka.clients.producer.{Callback, KafkaProducer, ProducerRecord, RecordMetadata}

import scala.concurrent.Promise
import scala.concurrent.duration.DurationInt
import scala.jdk.CollectionConverters._
import com.typesafe.scalalogging.LazyLogging

object AqicnProducer extends IOApp with LazyLogging {

  private val props: Map[String, Object] = Map(
    CLIENT_ID_CONFIG -> Configuration.kafkaProducerAppID,
    BOOTSTRAP_SERVERS_CONFIG -> Configuration.kafkaBootstrapServers,
    KEY_SERIALIZER_CLASS_CONFIG -> classOf[KafkaAvroSerializer],
    VALUE_SERIALIZER_CLASS_CONFIG -> classOf[KafkaAvroSerializer],
    SCHEMA_REGISTRY_URL_CONFIG -> Configuration.kafkaSchemaRegistryUrl,
    ACKS_CONFIG -> Configuration.kafkaProducerAcks
  )

  override def run(args: List[String]): IO[ExitCode] =
    Resource
      .make(IO(new KafkaProducer[IndexedRecord, IndexedRecord](props.asJava)))(p => IO(logger.info("closing producer...")) *> IO(p.close()))
      .use { producer =>
        Seq(
          IO(Engine.carEngine(AqicnApi(apiKey = args(0)))).flatMap(send(producer)(Configuration.kafkaAirQualityTopic, _)).foreverM,
        ).parSequence_.as(ExitCode.Success)
      }

  private def send[K, V](
                          producer: KafkaProducer[IndexedRecord, IndexedRecord]
                        )(topic: String, records: Seq[(K, V)])(implicit krf: RecordFormat[K], vrf: RecordFormat[V]): IO[Unit] =
    records.traverse {
      case (k, v) =>
        val p = Promise[Unit]()
        producer.send(
          new ProducerRecord[IndexedRecord, IndexedRecord](topic, krf.to(k), vrf.to(v)),
          new Callback {
            override def onCompletion(metadata: RecordMetadata, exception: Exception): Unit =
              Option(exception).map(p.failure).getOrElse(p.success(()))
          }
        )
        IO.fromFuture(IO(p.future)) *> IO(logger.info(s"produced aqi with key: [$k] to [$topic]")) *> IO.sleep(1.seconds)
    }.void
}
