import scala.collection.immutable.Seq

scalaVersion := "2.12.15"

name := "weather-insights"
version := "1.0"

libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "2.3.0"

libraryDependencies ++= Seq(
  "io.delta" %% "delta-spark" % "3.0.0",
  "io.delta" %% "delta-iceberg" % "3.0.0",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "2.2.0",
  "org.apache.spark" %% "spark-sql" % "3.5.0",
  "com.typesafe" % "config" % "1.4.3"
)

assembly / assemblyMergeStrategy := {
  case PathList("META-INF", _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

lazy val root = (project in file(".")).settings(
  name := "scala-sbt",
  version := "1.0",
  Compile / mainClass := Some("runner.Runner"),
  assembly / mainClass := Some("runner.Rune")
)