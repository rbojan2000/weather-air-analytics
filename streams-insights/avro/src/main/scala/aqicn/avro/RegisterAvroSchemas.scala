package aqicn.avro

import aqicn.config.Configuration
import com.typesafe.scalalogging.LazyLogging
import io.circe.generic.auto._
import sttp.client3.circe._
import sttp.client3.{HttpURLConnectionBackend, _}
import sttp.model.StatusCode

object RegisterAvroSchemas extends App with LazyLogging{

  case class RegisterSchemaRequest(schema: String)

  val backend = HttpURLConnectionBackend()

  private val subjects: Seq[(String, RegisterSchemaRequest)] = Seq(
    (s"${Configuration.kafkaAirQualityTopic}-key", RegisterSchemaRequest(airQualityIdSchema.toString())),
    (s"${Configuration.kafkaAirQualityTopic}-value", RegisterSchemaRequest(airQualityValueSchema.toString())),

    (s"${Configuration.kafkaCitiesTopic}-key", RegisterSchemaRequest(cityIdSchema.toString())),
    (s"${Configuration.kafkaCitiesTopic}-value", RegisterSchemaRequest(cityValueSchema.toString()))
  )

  private val statusCodes: Seq[(String, StatusCode)] = subjects.map {
    case (subject, schema) =>
      subject -> basicRequest
        .post(uri"${Configuration.kafkaSchemaRegistryUrl}/subjects/$subject/versions")
        .contentType("application/vnd.schemaregistry.v1+json")
        .body(schema)
        .send(backend)
        .code
  }

  statusCodes.foreach {
    case (subject, statusCode) =>
      logger.info(s"Register schema $subject, response code: $statusCode")
  }
}
