package com.apptikar.csvreadertask.presentation.home

import androidx.compose.ui.unit.Dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apptikar.csvreadertask.domain.model.ParsedFile
import com.apptikar.csvreadertask.domain.usecases.CsvFileParserUseCase
import com.apptikar.csvreadertask.domain.usecases.RecordsSorterUseCase
import com.apptikar.csvreadertask.domain.usecases.XlsFileParserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val csvFileParserUseCase: CsvFileParserUseCase,private val xlsFileParserUseCase: XlsFileParserUseCase
,private val recordsSorter : RecordsSorterUseCase) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _startFilePicker = MutableStateFlow(false)
    val startFilePicker = _startFilePicker.asStateFlow()

    private val _parsedFile = MutableStateFlow<List<ParsedFile>?>(null)
    val parsedFile = _parsedFile.asStateFlow()

    private  val _widthList = MutableStateFlow(arrayListOf<Dp>())
    val widthList = _widthList.asStateFlow()

    private val _drawListTrigger = MutableStateFlow(false)
    val drawListTrigger = _drawListTrigger.asStateFlow()

    private val _preview = MutableStateFlow(false)
    val preview = _preview.asStateFlow()






    fun setLoading(loading : Boolean)
    {
        _loading.update { loading }
    }

    fun setStartFilePicker(start : Boolean)
    {
        _startFilePicker.update { start }
    }


    fun setPreview(start : Boolean)
    {
        _preview.update { start }
    }

    fun setWidthWights(width : Dp)
    {

        _widthList.value.add(width)
        if (_widthList.value.size == 10) _drawListTrigger.update { true }

    }

    fun parseCsv(fileStream : InputStream){
        viewModelScope.launch(Dispatchers.IO) {
            _parsedFile.update {

                val parsedList = csvFileParserUseCase(fileStream)

                withContext(Dispatchers.Default)
                {
                    recordsSorter(parsedList)
                }

            }
        }
        _widthList.update { arrayListOf() }
        _drawListTrigger.update { false }
    }


    fun parseXls(type : String ,fileStream: InputStream) {

        viewModelScope.launch(Dispatchers.IO) {
            _parsedFile.update {
                val parsedList = xlsFileParserUseCase(type ,fileStream)
                withContext(Dispatchers.Default)
                {
                   recordsSorter(parsedList)
                }
            }
        }
        _widthList.update { arrayListOf() }
        _drawListTrigger.update { false }
    }


}