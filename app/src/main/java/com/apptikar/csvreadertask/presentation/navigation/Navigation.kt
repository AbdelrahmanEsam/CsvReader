package com.apptikar.csvreadertask.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.apptikar.csvreadertask.presentation.home.Home
import com.apptikar.csvreadertask.presentation.navigation.Destinations.Home

@Composable
fun ReaderNavGraph(
    navController: NavHostController,
    modifier: Modifier,
    scaffoldState: ScaffoldState,
) {



    NavHost(
        navController = navController,
        startDestination = Home,
        modifier = modifier
    ) {

        composable(Home){
            Home(modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                ,navController)
        }

    }


}