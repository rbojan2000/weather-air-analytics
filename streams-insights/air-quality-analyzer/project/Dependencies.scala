import sbt.*

object Dependencies {

  object V {
    val kafkaStreams = "7.4.0"
    val kafkaAvro = "6.2.0"
    val kafkaStreamsCommunity: String = kafkaStreams + "-ce"

    val logging = "3.9.5"
    val configTS = "1.4.3"
    val logback = "1.2.3"

    val scalaTestVersion = "3.2.12"
    val mockitoVersion = "1.16.0"
  }

  object Libs {
    val kafkaStreamsScala = "org.apache.kafka" %% "kafka-streams-scala" % V.kafkaStreamsCommunity
    val kafkaStreamsAvro = "io.confluent" % "kafka-streams-avro-serde" % V.kafkaAvro
    val configTypesafe = "com.typesafe" % "config" % V.configTS
    val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % V.logging
    val logback = "ch.qos.logback" % "logback-classic" % V.logback
    val scalaTest = "org.scalatest" %% "scalatest" % V.scalaTestVersion % "test"
    val mockito =  "org.mockito" %% "mockito-scala" % V.mockitoVersion % "test"
  }
}

