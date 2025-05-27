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
import androidx.compose.runtime.LaunchedEffect
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
import com.example.skybird.Modelo.BBDD.Avistamiento
import com.example.skybird.Modelo.BBDD.SkybirdDAO

@Composable
fun ListaAvistamientos(skybirdDAO: SkybirdDAO, volver: () -> Unit, sesionViewModel: SesionViewModel, avistamientoViewModel: AvistamientoViewModel, nuevoAvistamiento: () -> Unit, navDetAvistamiento: () -> Unit){

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
                text = "Avistamientos",
                fontSize = 35.sp,
                color = Color(0xFF1A2C47),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 16.dp)
            )

                MostrarAvistamientos(skybirdDAO, navDetAvistamiento, avistamientoViewModel)
        }

        if (sesionViewModel.usuarioActual.value?.admin == true) {
            Button(
                onClick = { nuevoAvistamiento() },
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
                    text = "Nuevo avistamiento"
                )
            }
        }
    }
}

@Composable
fun MostrarAvistamientos(skybirdDAO: SkybirdDAO, navDetAvistamiento: () -> Unit, avistamientoViewModel: AvistamientoViewModel) {
    //Obtenemos todos los avistamientos almacenados en la base de datos
    var listaAvistamientos = avistamientoViewModel.obtenerAvistamientos(skybirdDAO).collectAsState(initial = emptyList()).value

    avistamientoViewModel.buscarEspecieId(skybirdDAO)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .shadow(6.dp, shape = RoundedCornerShape(16.dp))
            .background(color = Color(0xFFDCEAF5), shape = RoundedCornerShape(16.dp))
            .padding(20.dp)
        ) {
        Column(Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                text = "Código: " + avistamientoViewModel.anillaSeleccionada.value?.codigoAnillamiento,
                fontSize = 24.sp,
                color = Color(0xFF1A2C47)
            )
            Text(
                text = "Especie: " + avistamientoViewModel.especieRecogida.value,
                fontSize = 16.sp,
                color = Color(0xFF2E2E2E)
            )
            Text(
                text = "Lugar de anillamiento: " + avistamientoViewModel.anillaSeleccionada.value?.lugar,
                fontSize = 16.sp,
                color = Color(0xFF2E2E2E)
            )
            Text(
                text = "Fecha de anillamiento: " + avistamientoViewModel.anillaSeleccionada.value?.fecha,
                fontSize = 16.sp,
                color = Color(0xFF2E2E2E)
            )
        }
    }

    if (listaAvistamientos.isEmpty()) {
        Text(
            text = "Todavía no hay avistamientos",
            color = Color.Black,
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 20.dp)
        )
    } else {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            listaAvistamientos.forEach { avistamiento ->
                AvistamientoItem(avistamiento, navDetAvistamiento, avistamientoViewModel)
            }
        }
    }
}

@Composable
fun AvistamientoItem(avistamiento: Avistamiento, navDetAvistamiento: () -> Unit, avistamientoViewModel: AvistamientoViewModel){

    Button(
        onClick = { avistamientoViewModel.avistamientoSeleccionado.value = avistamiento
            navDetAvistamiento() },
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF56658C),
            contentColor = Color.White
        )
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = avistamiento.fecha,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            )
            Text(
                text = avistamiento.lugar,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 0.dp)
                    .fillMaxWidth()
            )
        }
    }
}


