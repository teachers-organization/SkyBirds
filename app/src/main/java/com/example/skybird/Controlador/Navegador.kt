package com.example.skybird.Controlador

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.skybird.Controlador.ViewModels.ForoViewModel
import com.example.skybird.Controlador.ViewModels.RegistroViewModel
import com.example.skybird.Controlador.ViewModels.SesionViewModel
import com.example.skybird.Data.BBDD.SkybirdDAO
import com.example.skybird.View.AñadirPregunta
import com.example.skybird.View.Configuracion
import com.example.skybird.View.Foro
import com.example.skybird.View.Home
import com.example.skybird.View.InicioSesion
import com.example.skybird.View.MostrarPregunta
import com.example.skybird.View.Registro


@Composable
fun Navegador(SkybirdDAO: SkybirdDAO, modifier: Modifier = Modifier){
    val navController: NavHostController = rememberNavController()

    //Inicializamos los viewModels
    val registroViewModel = RegistroViewModel()
    val sesionViewModel = SesionViewModel()
    val foroViewModel = ForoViewModel()

    NavHost(navController = navController,
        startDestination = "InicioSesion"){

        composable(route = "InicioSesion"){
            InicioSesion(SkybirdDAO, sesionViewModel, crearCuenta = { navController.navigate("Registro") }, login = { navController.navigate("Home") })
        }
        composable(route = "Registro"){
            Registro(SkybirdDAO, registroViewModel, volver = { navController.navigate("InicioSesion") })
        }
        composable(route = "Home"){
            Home(sesionViewModel, config = { navController.navigate("Configuracion") }, foro = { navController.navigate("Foro") })
        }
        composable(route = "Configuracion"){
            Configuracion(SkybirdDAO, sesionViewModel, volver = { navController.navigate("Home") })
        }
        composable(route = "Foro"){
            Foro(SkybirdDAO, sesionViewModel, volver = { navController.navigate("Home") }, pregunta = { navController.navigate("AñadirPregunta") }, foroViewModel, navDetPregunta = { navController.navigate("MostrarPregunta") })
        }
        composable(route = "AñadirPregunta"){
            AñadirPregunta(SkybirdDAO, sesionViewModel, foroViewModel, volver = { navController.navigate("Foro") })
        }
        composable(route = "MostrarPregunta"){
            MostrarPregunta()
        }
    }



}



