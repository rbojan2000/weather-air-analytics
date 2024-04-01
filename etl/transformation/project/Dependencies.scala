import sbt.*

object Dependencies {
  object V {
    val deltaSpark = "3.0.0"
    val deltaIceberg = "3.0.0"
    val scalaParserCombinators = "2.3.0"
    val sparkSql = "3.5.0"
    val config = "1.4.3"
    val picocliVersion = "4.6.3"
    val logging = "3.9.5"
    val logback = "1.2.3"
  }

  object Libs {
    val deltaSpark = "io.delta" %% "delta-spark" % V.deltaSpark
    val deltaIceberg = "io.delta" %% "delta-iceberg" % V.deltaIceberg
    val scalaParserCombinators = "org.scala-lang.modules" %% "scala-parser-combinators" % V.scalaParserCombinators
    val sparkSql = "org.apache.spark" %% "spark-sql" % V.sparkSql
    val config = "com.typesafe" % "config" % V.config
    val picocli = "info.picocli" % "picocli" % V.picocliVersion
    val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % V.logging
    val logback = "ch.qos.logback" % "logback-classic" % V.logback
  }
}
