package com.apptikar.csvreadertask.domain.usecases

import android.util.Log
import com.apptikar.csvreadertask.domain.model.ParsedFile
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter



class RecordsSorterUseCase
{

    operator fun invoke(list : List<ParsedFile>) : List<ParsedFile>?
    {

        val newList = arrayListOf<ParsedFile>()
        newList.add(list.first())
       val formatter =  DateTimeFormatter.ofPattern("dd-MM-yyyy")

        newList.addAll(list.subList(1,list.lastIndex).groupBy {

            LocalDate.parse(it.date.toString().trim(),formatter).dayOfWeek
        }.flatMap {
            it.value
        })

      return   newList
    }

}