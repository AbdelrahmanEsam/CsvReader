package com.apptikar.csvreadertask.presentation.home


import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.documentfile.provider.DocumentFile
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.apptikar.csvreadertask.R
import com.apptikar.csvreadertask.domain.model.ParsedFile
import java.io.InputStream
import kotlin.reflect.full.memberProperties


@Composable
fun Home(modifier: Modifier , navController: NavController,viewModel: HomeViewModel = hiltViewModel()) {

    val loading = viewModel.loading.collectAsState()
    val startFilePicker = viewModel.startFilePicker.collectAsState()
    val parsedFile = viewModel.parsedFile.collectAsState()
    val preview = viewModel.preview.collectAsState()
    val context = LocalContext.current










    Column(modifier = modifier, verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {

        Button(onClick = {
            viewModel.setStartFilePicker(true)
            viewModel.setPreview(false)
            
        }) {
            Text(text = stringResource(id = if (loading.value) R.string.loading else {R.string.select}), color = Color.White)
        }

        parsedFile.value?.let { parsedList ->


            Button(onClick = {

                viewModel.setPreview(true)

            }) {
                Text(text = stringResource(R.string.showPreview), color = Color.White)
            }

            if (preview.value){
                PreviewTable(modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .horizontalScroll(
                        rememberScrollState()
                    ), contentList = parsedList,viewModel)
            }






        }
    }


    startActivityForResult(
        key =  startFilePicker.value,
        onDialogDismiss = { viewModel.setLoading(false);viewModel.setStartFilePicker(false) },
        launcher = { fileLauncher ->
            if (startFilePicker.value) {
                fileLauncher.launch("*/*")
            }
        },
        viewModel
    )


}


@Composable
fun startActivityForResult(
    key : Boolean,
    onDialogDismiss : () -> Unit,
    launcher: (ManagedActivityResultLauncher<String, Uri?>) -> Unit,
    viewModel: HomeViewModel
) {
  val errorMessage =   stringResource(R.string.file_error_message)
    val xls = stringResource(R.string.xls)
    val csv = stringResource(R.string.csv)
    val xlsx = stringResource(R.string.xlsx)
    val context = LocalContext.current
    val activityLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()){ uri ->
        try {
            val extension =
                DocumentFile.fromSingleUri(context, uri!!)?.name?.takeLastWhile { char ->
                    char != '.' }
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            if (extension != csv && extension != xlsx && extension != xls){
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                onDialogDismiss()
                return@rememberLauncherForActivityResult
            }else
            {
                if (extension == csv){
                    viewModel.parseCsv(inputStream!!)

                }else
                {
                    viewModel.parseXls(extension ,inputStream!!)
                }
                onDialogDismiss()
            }


            } catch (e:java.lang.Exception){
            Log.d("LOG", e.message.toString())
            onDialogDismiss()
        }
    }
    
    LaunchedEffect(key1 = key){
        if (key) launcher(activityLauncher)
    }

}





@Composable
fun PreviewTable(modifier: Modifier, contentList : List<ParsedFile>, viewModel: HomeViewModel) {



    val widthList = viewModel.widthList.collectAsState()
    val drawList = viewModel.drawListTrigger.collectAsState()
    Column {
            LazyColumn(modifier)
            {



                item {
                    Row(
                        Modifier
                            .wrapContentSize()
                    ) {
                        ParsedFile::class.memberProperties.forEachIndexed { index, kProperty1 ->
                            ResponsiveText(
                                text = kProperty1.get(contentList[0]).toString(),
                                modifier = Modifier
                                    .wrapContentSize()
                                    .then(if (drawList.value) Modifier.width(widthList.value[index]) else Modifier)
                                    .border(1.dp, Color.Black)
                                    .padding(8.dp),
                                targetTextSize = 10.sp
                            ) { width ->

                                viewModel.setWidthWights(width)
                            }
                        }
                    }
                }

                if (drawList.value) {
                    items(contentList.size - 2) { index ->
                        val row1 = contentList[index+1]
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            ParsedFile::class.memberProperties.forEachIndexed { index, kProperty1 ->

                                ResponsiveText(
                                    text = kProperty1.get(row1).toString(),
                                    modifier = Modifier
                                        .width(widthList.value[index])
                                        .border(1.dp, Color.Black)
                                        .padding(8.dp),
                                    targetTextSize = 10.sp
                                ) { width ->
                                    viewModel.setWidthWights(width)
                                }
                            }


                        }
                    }
                }
            }
        }
    }





@Composable
fun ResponsiveText(
    text : String,
    maxLine : Int = 1,
    modifier: Modifier,
    targetTextSize : TextUnit,
    widthCallback : (width : Dp) -> Unit
) {
    var textSize :TextUnit by remember{ mutableStateOf(targetTextSize) }

    Text(modifier = modifier.onGloballyPositioned {  layoutCoordinates ->

        val width = layoutCoordinates.size.width.dp
        widthCallback(width)

    },text = text, maxLines = maxLine, fontSize = textSize ,onTextLayout = { textLayoutResult ->

        val currentMaxLine = textLayoutResult.lineCount-1

        if (textLayoutResult.isLineEllipsized(currentMaxLine)) textSize *= 0.9f


    }, color = Color.Black, textAlign = TextAlign.Center)
}






