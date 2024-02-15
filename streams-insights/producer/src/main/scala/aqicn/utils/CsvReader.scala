package aqicn.utils

import aqicn.avro.message.City
import au.com.bytecode.opencsv.CSVReader

import java.io.FileReader

object CsvReader {
  def readCities(filePath: String): Seq[City] = {
    val reader = new CSVReader(new FileReader(filePath))
    var csvData: Seq[City] = Seq()

    try {
      reader.readNext()
      var line: Array[String] = reader.readNext()

      while (line != null) {
        val rowData = City(
          id = line(0).toInt,
          name = line(1),
          lat = line(2),
          lon = line(3),
          country = line(4),
          population = line(5).toLong
        )
        csvData :+= rowData
        line = reader.readNext()
      }
    } finally {
      reader.close()
    }

    csvData
  }
}
