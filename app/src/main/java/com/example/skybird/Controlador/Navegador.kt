package com.example.skybird.Controlador

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.skybird.Controlador.ViewModels.AvesViewModel
import com.example.skybird.Controlador.ViewModels.AvistamientoViewModel
import com.example.skybird.Controlador.ViewModels.ForoViewModel
import com.example.skybird.Controlador.ViewModels.RegistroViewModel
import com.example.skybird.Controlador.ViewModels.SesionViewModel
import com.example.skybird.Modelo.BBDD.SkybirdDAO
import com.example.skybird.Vista.AdminUsuarios
import com.example.skybird.Vista.AñadirPregunta
import com.example.skybird.Vista.BorrarCuenta
import com.example.skybird.Vista.CambioContraseña
import com.example.skybird.Vista.CambioNick
import com.example.skybird.Vista.CambioNombre
import com.example.skybird.Vista.Configuracion
import com.example.skybird.Vista.DetallesAve
import com.example.skybird.Vista.DetallesAvistamiento
import com.example.skybird.Vista.Diccionario
import com.example.skybird.Vista.Foro
import com.example.skybird.Vista.Home
import com.example.skybird.Vista.InicioSesion
import com.example.skybird.Vista.ListaAnillamiento
import com.example.skybird.Vista.ListaAvistamientos
import com.example.skybird.Vista.MostrarPregunta
import com.example.skybird.Vista.NuevoAnillamiento
import com.example.skybird.Vista.NuevoAvistamiento
import com.example.skybird.Vista.Registro
import com.example.skybird.Vista.ResponderPregunta


@Composable
fun Navegador(SkybirdDAO: SkybirdDAO, modifier: Modifier = Modifier) {
    val navController: NavHostController = rememberNavController()

    //Inicializamos los viewModels
    val registroViewModel = RegistroViewModel()
    val sesionViewModel = SesionViewModel()
    val foroViewModel = ForoViewModel()
    val avesViewModel = AvesViewModel()
    val avistamientoViewModel = AvistamientoViewModel()

    NavHost(
        navController = navController,
        startDestination = "InicioSesion"
    ) {

        composable(route = "InicioSesion") {
            InicioSesion(
                SkybirdDAO,
                sesionViewModel,
                crearCuenta = { navController.navigate("Registro") },
                login = { navController.navigate("Home") })
        }
        composable(route = "Registro") {
            Registro(
                SkybirdDAO,
                registroViewModel,
                volver = { navController.navigate("InicioSesion") },
                sesionViewModel)
        }
        composable(route = "Home") {
            Home(
                sesionViewModel,
                config = { navController.navigate("Configuracion") },
                foro = { navController.navigate("Foro") },
                inicioSesion = { navController.navigate("InicioSesion") },
                diccionarioAves = { navController.navigate("DiccionarioAves") },
                adminUsers = { navController.navigate("AdminUsers") },
                listaAnillamientos = { navController.navigate("ListaAnillamientos") })
        }
        composable(route = "Configuracion") {
            Configuracion(
                sesionViewModel,
                borrarCuenta = { navController.navigate("BorrarCuenta") },
                cambioContraseña = { navController.navigate("CambioContraseña") },
                cambioNick = { navController.navigate("CambioNick") },
                cambioNombre = { navController.navigate("CambioNombre") },
                volver = { navController.navigate("Home") })
        }
        composable(route = "BorrarCuenta") {
            BorrarCuenta(
                SkybirdDAO,
                sesionViewModel,
                volver = { navController.navigate("Configuracion") },
                inicio = { navController.navigate("InicioSesion") })
        }
        composable(route = "CambioContraseña") {
            CambioContraseña (
                SkybirdDAO,
                sesionViewModel,
                volver = { navController.navigate("Configuracion") })
        }
        composable(route = "CambioNick") {
            CambioNick (
                SkybirdDAO,
                sesionViewModel,
                volver = { navController.navigate("Configuracion") })
        }
        composable(route = "CambioNombre") {
            CambioNombre (
                SkybirdDAO,
                sesionViewModel,
                volver = { navController.navigate("Configuracion") })
        }
        composable(route = "Foro") {
            Foro(
                SkybirdDAO,
                volver = { navController.navigate("Home") },
                pregunta = { navController.navigate("AñadirPregunta") },
                foroViewModel,
                navDetPregunta = { navController.navigate("MostrarPregunta") })
        }
        composable(route = "AñadirPregunta") {
            AñadirPregunta(
                SkybirdDAO,
                sesionViewModel,
                foroViewModel,
                volver = { navController.navigate("Foro") })
        }
        composable(route = "MostrarPregunta") {
            MostrarPregunta(
                SkybirdDAO,
                volver = { navController.navigate("Foro") },
                foroViewModel,
                sesionViewModel,
                responder = { navController.navigate("ResponerPregunta") })
        }
        composable(route = "ResponerPregunta") {
            ResponderPregunta(
                SkybirdDAO,
                volver = { navController.navigate("MostrarPregunta") },
                foroViewModel,
                sesionViewModel
            )
        }
        composable(route = "DiccionarioAves") {
            Diccionario(
                volver = { navController.navigate("Home") },
                navDetPajaro = { navController.navigate("DetallesAve") },
                avesViewModel
            )
        }
        composable(route = "DetallesAve") {
            DetallesAve(volver = { navController.navigate("DiccionarioAves") }, avesViewModel)
        }
        composable(route = "AdminUsers") {
            AdminUsuarios(volver = { navController.navigate("Home") }, SkybirdDAO, sesionViewModel)
        }
        composable(route = "ListaAnillamientos") {
            ListaAnillamiento(
                SkybirdDAO,
                volver = { navController.navigate("Home") },
                sesionViewModel,
                avistamientoViewModel,
                nuevoAnilla = { navController.navigate("NuevoAnillamiento") },
                avistamientosAnilla = { navController.navigate("AvistamientosAnilla") })
        }
        composable(route = "NuevoAnillamiento") {
            NuevoAnillamiento(
                SkybirdDAO,
                avistamientoViewModel,
                volver = { navController.navigate("ListaAnillamientos") },
                sesionViewModel
            )
        }
        composable(route = "AvistamientosAnilla") {
            ListaAvistamientos(
                SkybirdDAO,
                volver = { navController.navigate("ListaAnillamientos") },
                sesionViewModel,
                avistamientoViewModel,
                nuevoAvistamiento = { navController.navigate("NuevoAvistamiento") },
                navDetAvistamiento = { navController.navigate("DetallesAvistamiento") })
        }
        composable(route = "NuevoAvistamiento") {
            NuevoAvistamiento(
                SkybirdDAO,
                avistamientoViewModel,
                volver = { navController.navigate("AvistamientosAnilla") })
        }
        composable(route = "DetallesAvistamiento") {
            DetallesAvistamiento(
                volver = { navController.navigate("AvistamientosAnilla") },
                avistamientoViewModel
            )
        }
    }
}



