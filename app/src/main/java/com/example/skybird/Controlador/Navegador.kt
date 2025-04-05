package com.example.skybird.Controlador

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


@Composable
fun Navegador(modifier: Modifier = Modifier){
    val navController: NavHostController = rememberNavController()
    //Inicializamos los viewModels
    //val homeScreenViewModel = HomeScreenViewModel(LocalContext.current)
    //val detailsScreenViewModel = DetailsScreenViewModel()
    NavHost(navController = navController,
        startDestination = "HomeScreen"){

        composable(route = "HomeScreen"){
            HomeScreen()
        }
    }



}



