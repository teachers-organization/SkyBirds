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
import com.example.skybird.Controlador.ViewModels.ForoViewModel
import com.example.skybird.Data.BBDD.Questions
import com.example.skybird.Data.BBDD.SkybirdDAO

@Composable
fun Foro(skybirdDAO: SkybirdDAO, volver: () -> Unit, pregunta: () -> Unit, foroViewModel: ForoViewModel, navDetPregunta: () -> Unit){

    val filtrarAutor = remember { mutableStateOf("") }
    val filtrarTitulo = remember { mutableStateOf("") }

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
                text = "Foro",
                fontSize = 35.sp,
                color = Color(0xFF1A2C47),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 16.dp)
            )

            Row(Modifier.fillMaxWidth()) {
                TextField(
                    value = filtrarTitulo.value,
                    onValueChange = { filtrarTitulo.value = it },
                    modifier = Modifier.weight(1f)
                        .padding(2.dp),
                    shape = RoundedCornerShape(8.dp),
                    placeholder = { Text("Filtrar por título...", color = Color.Gray) }
                )
            }

                MostrarDudas(skybirdDAO, foroViewModel, navDetPregunta, filtrarTitulo.value)

        }

        Button(
            onClick = { pregunta() },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFA3B18A),
                contentColor = Color.White
            ),
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(30.dp)
        ) {
            Text(text = "+",
                fontSize = 35.sp)
        }
    }

}

@Composable
fun MostrarDudas(skybirdDAO: SkybirdDAO, foroViewModel: ForoViewModel, navDetPregunta: () -> Unit, filtrarTitulo: String) {
    //Obtenemos todas las dudas almacenadas en la base de datos
    var listaPreguntas = foroViewModel.obtenerPreguntas(skybirdDAO).collectAsState(initial = emptyList()).value

    if (listaPreguntas.isEmpty()) {
        Text(
            text = "Todavía no hay preguntas",
            color = Color.Black,
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 20.dp)
        )
    } else {
        if (filtrarTitulo != ""){
            listaPreguntas = foroViewModel.filtrarTitulo(listaPreguntas, filtrarTitulo)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            listaPreguntas.reversed().forEach { pregunta ->
                PreguntaItem(pregunta, navDetPregunta, foroViewModel)
            }
        }
    }
}

@Composable
fun PreguntaItem(question: Questions, navDetPregunta: () -> Unit, foroViewModel: ForoViewModel){

    val timestamp = System.currentTimeMillis()
    val diferenciaTiempo = timestamp - question.fechaCreacion
    val tiempoFormateado = foroViewModel.formatearTiempoTranscurrido(diferenciaTiempo)

    Button(
        onClick = { foroViewModel.preguntaSeleccionada.value = question
            navDetPregunta() },
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
            text = question.titulo + "\n- " + tiempoFormateado + " -",
            fontSize = 18.sp,
            modifier = Modifier.padding(8.dp)
                .fillMaxWidth()
        )
    }
}



