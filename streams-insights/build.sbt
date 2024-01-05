import Dependencies.*

name := "streams-insights"
version := "0.1"

lazy val commonSettings = Seq(
  scalaVersion := "2.13.11",
  resolvers += "Confluent Maven Repository" at "https://packages.confluent.io/maven/"
)

lazy val root = (project in file(".")).aggregate(producer, airQualityAnalyzer, avro, domain)

lazy val producer = (project in file("producer"))
  .settings(commonSettings)
  .settings(
    name := "aqicn-air-quality-producer",
    libraryDependencies ++= Seq(Libs.kafkaClient, Libs.kafkaAvro, Libs.catsEffect, Libs.sl4j, Libs.logback, Libs.scalaLogging, Libs.akkaStream, Libs.playJson, Libs.openCsv, Libs.picocli)
  )
  .dependsOn(domain, avro)


lazy val airQualityAnalyzer = (project in file("air-quality-analyzer"))
  .settings(commonSettings)
  .settings(
    name := "air-quality-analyzer",
    libraryDependencies ++= Seq(Libs.kafkaStreamsScala, Libs.kafkaStreamsAvro, Libs.avro4sKafka, Libs.scalaLogging),
    assembly / assemblyMergeStrategy := {
      case PathList("META-INF", xs@_*) =>
        xs map {
          _.toLowerCase
        } match {
          case "services" :: xs =>
            MergeStrategy.filterDistinctLines
          case _ => MergeStrategy.discard
        }

      case _ => MergeStrategy.first
    },
    assembly / assemblyJarName := s"${name.value}-${version.value}.jar"
  )
  .dependsOn(domain, avro)

lazy val avro = (project in file("avro"))
  .settings(commonSettings)
  .settings(
    name := "avro",
    libraryDependencies ++= Seq(Libs.avro4sCore, Libs.sttp3Core, Libs.sttp3Circe, Libs.circeGeneric, Libs.smlTagging, Libs.sl4j, Libs.logback, Libs.scalaLogging)
  )
  .dependsOn(domain)

lazy val domain = (project in file("domain"))
  .settings(commonSettings)
  .settings(
    name := "domain",
    libraryDependencies ++= Seq(Libs.configTypesafe)
  )