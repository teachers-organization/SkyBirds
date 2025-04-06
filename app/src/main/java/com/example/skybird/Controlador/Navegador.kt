package com.example.skybird.Controlador

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.skybird.View.inicioSesion
import com.example.skybird.View.registro


@Composable
fun Navegador(modifier: Modifier = Modifier){
    val navController: NavHostController = rememberNavController()
    //Inicializamos los viewModels
    //val homeScreenViewModel = HomeScreenViewModel(LocalContext.current)
    //val detailsScreenViewModel = DetailsScreenViewModel()
    NavHost(navController = navController,
        startDestination = "InicioSesion"){

        composable(route = "InicioSesion"){
            inicioSesion()
        }
        composable(route = "Registro"){
            registro()
        }
    }



}



