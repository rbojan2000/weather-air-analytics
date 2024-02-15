package aqicn.producer

import aqicn.config.Configuration
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig
import org.apache.kafka.clients.producer.ProducerConfig

import java.util.Properties

trait Producer {
  def props(): Properties = {
    val props = new Properties()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Configuration.bootstrapServers)
    props.put(ProducerConfig.ACKS_CONFIG, Configuration.acks)
    props.put(
      AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG,
      Configuration.schemaRegistryUrl
    )
    props.put(
      ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
      Configuration.keySerializer
    )
    props.put(
      ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
      Configuration.valueSerializer
    )
    props
  }

  def produce(): Unit
}
