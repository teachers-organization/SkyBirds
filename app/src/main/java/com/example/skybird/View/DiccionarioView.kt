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
import com.example.skybird.Controlador.ViewModels.AvesViewModel
import com.example.skybird.Controlador.ViewModels.ForoViewModel
import com.example.skybird.Modelo.API.Bird
import com.example.skybird.Modelo.BBDD.Questions
import com.example.skybird.Modelo.BBDD.SkybirdDAO

@Composable
fun Diccionario(volver: () -> Unit, navDetPajaro: () -> Unit, avesViewModel: AvesViewModel){

    val filtrarNombre = remember { mutableStateOf("") }
    //Observamos la variable para que cuando la corrutina termine nos devuelve la lista de aves
    val listaAves = avesViewModel.aves.value

    //Llamamos a obtenerAves solo una vez al entrar en la pantalla
    LaunchedEffect(Unit) {
        avesViewModel.obtenerAves()
    }

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
                text = "Diccionario de aves",
                fontSize = 35.sp,
                color = Color(0xFF1A2C47),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 16.dp)
            )

            Row(Modifier.fillMaxWidth()) {
                TextField(
                    value = filtrarNombre.value,
                    onValueChange = { filtrarNombre.value = it },
                    modifier = Modifier.weight(1f)
                        .padding(2.dp),
                    shape = RoundedCornerShape(8.dp),
                    placeholder = { Text("Filtrar por nombre...", color = Color.Gray) }
                )
            }

            MostrarAves(navDetPajaro, filtrarNombre.value, listaAves)

        }
    }
}

@Composable
fun MostrarAves(navDetPajaro: () -> Unit, filtrarTitulo: String, listaAves: List<Bird>) {

    if (listaAves.isEmpty()) {
        Text(
            text = "Cargando...",
            color = Color.Black,
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 20.dp)
        )
    } else {
        /*
        if (filtrarTitulo != ""){
            listaPreguntas = foroViewModel.filtrarTitulo(listaPreguntas, filtrarTitulo)
        }*/

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            listaAves.forEach { ave ->
                PajaroItem(ave, navDetPajaro)
            }
        }
    }
}

@Composable
fun PajaroItem(ave: Bird, navDetPajaro: () -> Unit){

    Button(
        onClick = {
            navDetPajaro() },
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
            text = ave.name,
            fontSize = 18.sp,
            modifier = Modifier.padding(8.dp)
                .fillMaxWidth()
        )
    }
}



