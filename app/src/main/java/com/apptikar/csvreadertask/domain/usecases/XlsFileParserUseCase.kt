package com.apptikar.csvreadertask.domain.usecases

import com.apptikar.csvreadertask.domain.model.ParsedFile
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.poifs.filesystem.POIFSFileSystem
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.InputStream

class XlsFileParserUseCase {


    operator fun invoke(type:String,inputStream: InputStream) : List<ParsedFile>
    {

        val workbook: Workbook = if (type == "xls"){HSSFWorkbook(POIFSFileSystem(inputStream))} else{XSSFWorkbook(inputStream)}
        val mySheet  = workbook.getSheetAt(0)
        return mySheet.mapNotNull { line ->
            ParsedFile(
                callingNumber = line?.getCell(0).toString(),
                callingNumberTwo = line?.getCell(1).toString(),
                date =line?.getCell(2).toString(),
                time = line?.getCell(3).toString() ,
                Duration = line?.getCell(4).toString(),
                callType = line?.getCell(5).toString(),
                IMEI = line?.getCell(6).toString(),
                IMSI = line?.getCell(7).toString(),
                firstCellID = line?.getCell(8).toString() ,
                secondCellID = line?.getCell(9).toString() ,
            )
        }

    }

}