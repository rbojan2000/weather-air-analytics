import sbt._

object Dependencies {

  object V {
    val sttp3 = "3.3.11"
    val kafkaStreams = "3.3.1"
    val kafkaAvro = "6.2.0"
    val logging = "3.9.5"
    val sl4j = "1.7.32"
    val logback = "1.2.3"
    val akkaStream = "2.7.0"
    val playJson = "2.10.0"
    val openCsv = "2.3"
    val picocliVersion = "4.6.3"
  }

  object Libs {
    val kafkaClient = "org.apache.kafka" % "kafka-clients" % V.kafkaStreams
    val kafkaAvro = "io.confluent" % "kafka-avro-serializer" % V.kafkaAvro

    val sl4j = "org.slf4j" % "slf4j-api" % V.sl4j
    val logback = "ch.qos.logback" % "logback-classic" % V.logback
    val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % V.logging
    val akkaStream = "com.typesafe.akka" %% "akka-stream" % V.akkaStream
    val playJson = "com.typesafe.play" %% "play-json" % V.playJson
    val openCsv = "net.sf.opencsv" % "opencsv" % V.openCsv
    val picocli = "info.picocli" % "picocli" % V.picocliVersion
    val sttp3Core = "com.softwaremill.sttp.client3" %% "core" % V.sttp3
    val sttp3Circe = "com.softwaremill.sttp.client3" %% "circe" % V.sttp3

  }
}
