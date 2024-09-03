import Dependencies.*


lazy val commonSettings = Seq(
  scalaVersion := "2.13.11",
  resolvers += "Confluent Maven Repository" at "https://packages.confluent.io/maven/"
)

lazy val root = (project in file("."))
  .settings(commonSettings)
  .settings(
    name := "air-quality-analyzer",
    libraryDependencies ++= Seq(
      Libs.kafkaStreamsScala,
      Libs.kafkaStreamsAvro,
      Libs.scalaLogging,
      Libs.configTypesafe,
      Libs.logback,
      Libs.scalaTest,
      Libs.mockito
    ),
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
