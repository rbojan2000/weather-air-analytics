import Dependencies._

ThisBuild / version := "0.1.0"
ThisBuild / scalaVersion := "2.13.11"

lazy val commonSettings = Seq(
  scalaVersion := "2.13.11",
  resolvers += "Confluent Maven Repository" at "https://packages.confluent.io/maven/"
)

lazy val root = (project in file("."))
  .settings(commonSettings)
  .settings(
    name := "producer",
    libraryDependencies ++= Seq(
      Libs.kafkaClient,
      Libs.kafkaAvro,
      Libs.sl4j,
      Libs.logback,
      Libs.scalaLogging,
      Libs.akkaStream,
      Libs.playJson,
      Libs.openCsv,
      Libs.picocli,
      Libs.sttp3Circe,
      Libs.sttp3Core
    ),
    Compile / sourceGenerators += (Compile / avroScalaGenerateSpecific).taskValue,
    assembly / assemblyMergeStrategy := {
      case PathList("META-INF", xs @ _*) =>
        xs map {
          _.toLowerCase
        } match {
          case "services" :: xs =>
            MergeStrategy.filterDistinctLines
          case _ => MergeStrategy.discard
        }

      case _ => MergeStrategy.first
    },
    assembly / assemblyJarName := s"${name.value}-${version.value}.jar",
    assembly / test := {}
  )
