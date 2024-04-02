import Dependencies.*

lazy val commonSettings = Seq(
  scalaVersion := "2.12.15",
  resolvers += "Confluent Maven Repository" at "https://packages.confluent.io/maven/",
  name := "load",
  version := "0.1"
)

lazy val root = (project in file("."))
  .settings(commonSettings)
  .settings(
    libraryDependencies ++= Seq(
      Libs.deltaIceberg,
      Libs.deltaSpark,
      Libs.scalaParserCombinators,
      Libs.sparkSql,
      Libs.config,
      Libs.scalaLogging,
      Libs.picocli,
      Libs.scalaLogging,
      Libs.logback
    ),
    Compile / mainClass := Some("Runner"),
    assembly / mainClass := Some("Runner"),
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
