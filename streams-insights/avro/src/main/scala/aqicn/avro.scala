package aqicn

import aqicn.domain._
import com.sksamuel.avro4s.{AvroSchema, RecordFormat}
import com.softwaremill.tagging._
import org.apache.avro.Schema

package object avro {
  type KeyRFTag
  type KeyRecordFormat[K] = RecordFormat[K] @@ KeyRFTag

  type ValueRFTag
  type ValueRecordFormat[V] = RecordFormat[V] @@ ValueRFTag

  val airQualityIdSchema: Schema = AvroSchema[AirQualityId]
  val airQualityValueSchema: Schema = AvroSchema[AirQualityValue]

  implicit val airQualityIdRF: KeyRecordFormat[AirQualityId] = RecordFormat[AirQualityId].taggedWith[KeyRFTag]
  implicit val airQualityValueRF: ValueRecordFormat[AirQualityValue] = RecordFormat[AirQualityValue].taggedWith[ValueRFTag]

}
