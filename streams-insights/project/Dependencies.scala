import sbt.*

object Dependencies {

  object V {
    val kafkaStreams = "7.4.0"
    val kafkaAvro = "6.2.0"
    val avro4s = "4.0.5"
    val sttp3 = "3.3.11"
    val circe = "0.14.1"
    val cats = "3.2.5"
    val smlCommon = "2.3.1"
    val kafkaStreamsCommunity: String = kafkaStreams + "-ce"
    val logging = "3.9.5"
    val configTS = "1.4.3"
    val logback = "1.2.3"
    val sl4j = "1.7.32"
    val akkaStream = "2.7.0"
    val playJson = "2.10.0"
    val openCsv = "2.3"
    val picocliVersion = "4.6.3"
  }

  object Libs {
    val kafkaClient = "org.apache.kafka" % "kafka-clients" % V.kafkaStreamsCommunity
    val kafkaStreams = "org.apache.kafka" % "kafka-streams" % V.kafkaStreams
    val kafkaStreamsScala = "org.apache.kafka" %% "kafka-streams-scala" % V.kafkaStreamsCommunity
    val kafkaAvro = "io.confluent" % "kafka-avro-serializer" % V.kafkaAvro
    val kafkaStreamsAvro = "io.confluent" % "kafka-streams-avro-serde" % V.kafkaAvro

    val avro4sCore = "com.sksamuel.avro4s" % "avro4s-core_2.13" % V.avro4s
    val avro4sKafka = "com.sksamuel.avro4s" % "avro4s-kafka_2.13" % V.avro4s

    val sttp3Core = "com.softwaremill.sttp.client3" %% "core" % V.sttp3
    val sttp3Circe = "com.softwaremill.sttp.client3" %% "circe" % V.sttp3
    val circeGeneric = "io.circe" %% "circe-generic" % V.circe

    val catsEffect = "org.typelevel" %% "cats-effect" % V.cats
    val configTypesafe = "com.typesafe" % "config" % V.configTS

    val smlTagging = "com.softwaremill.common" %% "tagging" % V.smlCommon
    val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % V.logging
    val sl4j = "org.slf4j" % "slf4j-api" % V.sl4j
    val logback = "ch.qos.logback" % "logback-classic" % V.logback
    val akkaStream = "com.typesafe.akka" %% "akka-stream" % V.akkaStream
    val playJson = "com.typesafe.play" %% "play-json" % V.playJson
    val openCsv = "net.sf.opencsv" % "opencsv" % V.openCsv
    val picocli = "info.picocli" % "picocli" % V.picocliVersion
  }
}