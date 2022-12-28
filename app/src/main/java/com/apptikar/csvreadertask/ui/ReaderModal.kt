package com.apptikar.csvreadertask.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.apptikar.csvreadertask.presentation.navigation.ReaderNavGraph
import com.apptikar.csvreadertask.ui.theme.CsvReaderTaskTheme

@Composable
fun ReaderModal() {

    CsvReaderTaskTheme {
        val navController = rememberNavController()
        val scaffoldState = rememberScaffoldState()

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Scaffold(
                scaffoldState = scaffoldState,
            ) { paddingValues ->

                Row(Modifier.fillMaxSize()) {
                    ReaderNavGraph(
                        navController = navController,
                        modifier = Modifier.padding(paddingValues), scaffoldState = scaffoldState,
                    )


                }
            }
        }

    }
}