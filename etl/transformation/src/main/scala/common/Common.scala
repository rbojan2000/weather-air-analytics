package common

import com.typesafe.scalalogging.LazyLogging
import io.delta.tables.DeltaTable
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}
import utils.Utils

trait Common extends Utils with LazyLogging {

  def saveTableIfNotExist(data: DataFrame, dType: String)
                         (implicit spark: SparkSession): Unit = {
    if (!DeltaTable.isDeltaTable(spark, getSilverTablePath(dType))) {
      data
        .write
        .format("delta")
        .mode(SaveMode.Overwrite)
        .option("mergeSchema", "true")
        .save(getSilverTablePath(dType))
      return
    }
  }

  def readCsv(path: String,
              schema: org.apache.spark.sql.types.StructType)
             (implicit spark: SparkSession): DataFrame = {
    spark.read
      .option("delimiter", ",")
      .option("header", "true")
      .schema(schema)
      .csv(path)
  }

}
