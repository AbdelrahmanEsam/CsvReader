package com.apptikar.csvreadertask.domain.usecases

import com.apptikar.csvreadertask.domain.model.ParsedFile
import com.opencsv.CSVReader
import java.io.InputStream
import java.io.InputStreamReader

class CsvFileParserUseCase {

    operator fun invoke(stream: InputStream): List<ParsedFile> {
        val csvReader = CSVReader(InputStreamReader(stream))
        val reader = csvReader
            .readAll()
            .mapNotNull { line ->
                ParsedFile(
                    callingNumber = line.getOrNull(0) ?: return@mapNotNull null,
                    callingNumberTwo = line.getOrNull(1) ?: return@mapNotNull null,
                    date = line.getOrNull(2) ?: return@mapNotNull null,
                    time = line.getOrNull(3) ?: return@mapNotNull null,
                    Duration = line.getOrNull(4) ?: return@mapNotNull null,
                    callType = line.getOrNull(5) ?: return@mapNotNull null,
                    IMEI = line.getOrNull(6) ?: return@mapNotNull null,
                    IMSI = line.getOrNull(7) ?: return@mapNotNull null,
                    firstCellID = line.getOrNull(8) ?: return@mapNotNull null,
                    secondCellID = line.getOrNull(9) ?: return@mapNotNull null,
                )
            }
            .also {

                csvReader.close()
            }
        return reader
    }

}