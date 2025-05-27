package com.example.skybird.View

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skybird.Controlador.ViewModels.AvistamientoViewModel
import com.example.skybird.Controlador.ViewModels.SesionViewModel
import com.example.skybird.Modelo.BBDD.Anillamiento
import com.example.skybird.Modelo.BBDD.SkybirdDAO

@Composable
fun ListaAnillamiento(skybirdDAO: SkybirdDAO, volver: () -> Unit, sesionViewModel: SesionViewModel, avistamientoViewModel: AvistamientoViewModel, nuevoAnilla: () -> Unit, avistamientosAnilla: () -> Unit){

    val filtrarNumAnilla = remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(top = 60.dp, bottom = 30.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            Button(
                onClick = { volver() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFA3B18A),
                    contentColor = Color.White
                ),
                modifier = Modifier.align(Alignment.Start)
            ) {
                Text("Volver")
            }

            Text(
                text = "Anillas",
                fontSize = 35.sp,
                color = Color(0xFF1A2C47),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 16.dp)
            )

            Row(Modifier.fillMaxWidth()) {
                TextField(
                    value = filtrarNumAnilla.value,
                    onValueChange = { filtrarNumAnilla.value = it },
                    modifier = Modifier.weight(1f)
                        .padding(2.dp),
                    shape = RoundedCornerShape(8.dp),
                    placeholder = { Text("Filtrar por número de anilla...", color = Color.Gray) }
                )
            }

            MostrarAnillas(skybirdDAO, avistamientosAnilla, filtrarNumAnilla.value, avistamientoViewModel)

        }

        if (sesionViewModel.usuarioActual.value?.admin == true) {
            Button(
                onClick = { nuevoAnilla() },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFA3B18A),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(30.dp)
            ) {
                Text(
                    text = "Nuevo anillamiento"
                )
            }
        }
    }
}

@Composable
fun MostrarAnillas(skybirdDAO: SkybirdDAO, navDetAnilla: () -> Unit, filtrarNumAnilla: String, avistamientoViewModel: AvistamientoViewModel) {
    //Obtenemos todas las anillas almacenadas en la base de datos
    var listaAnillas = avistamientoViewModel.obtenerAnillas(skybirdDAO).collectAsState(initial = emptyList()).value

    if (listaAnillas.isEmpty()) {
        Text(
            text = "Todavía no hay anillamientos",
            color = Color.Black,
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 20.dp)
        )
    } else {
        if (filtrarNumAnilla != ""){
            listaAnillas = avistamientoViewModel.filtrarAnillamientos(listaAnillas, filtrarNumAnilla)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            listaAnillas.forEach { anilla ->
                AnillaItem(anilla, navDetAnilla, avistamientoViewModel)
            }
        }
    }
}

@Composable
fun AnillaItem(anilla: Anillamiento, navDetAnilla: () -> Unit, avistamientoViewModel: AvistamientoViewModel){

    Button(
        onClick = { avistamientoViewModel.anillaSeleccionada.value = anilla
                  navDetAnilla() },
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF56658C),
            contentColor = Color.White
        )
    ) {
        Text(
            text = anilla.codigoAnillamiento,
            fontSize = 18.sp,
            modifier = Modifier.padding(8.dp)
                .fillMaxWidth()
        )
    }
}


