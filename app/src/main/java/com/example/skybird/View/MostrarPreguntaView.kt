package com.example.skybird.View

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skybird.Controlador.ViewModels.ForoViewModel
import com.example.skybird.Controlador.ViewModels.SesionViewModel
import com.example.skybird.Data.BBDD.Answers
import com.example.skybird.Data.BBDD.SkybirdDAO

@Composable
fun MostrarPregunta(skybirdDAO: SkybirdDAO, volver: () -> Unit, foroViewModel: ForoViewModel, sesionViewModel: SesionViewModel) {

    val esAutor = foroViewModel.esAutor(sesionViewModel)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
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
                    containerColor = Color(0xFF5A7391),
                    contentColor = Color.White
                ),
                modifier = Modifier.align(Alignment.Start)
            ) {
                Text("Volver")
            }

            MostrarDudaYRespuestas(foroViewModel, skybirdDAO)

        }
        Button(
            onClick = {  },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFA3B18A),
                contentColor = Color.White
            ),
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(30.dp)
        ) {
            Text(text = "Responder",
                fontSize = 20.sp)
        }
        if (esAutor){
            Button(
                onClick = {
                    foroViewModel.borrarPregunta(skybirdDAO)
                    volver()
                },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFA3B18A),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(30.dp)
            ) {
                Text(text = "Borrar",
                    fontSize = 20.sp)
            }
        }
    }
}

@Composable
fun MostrarDudaYRespuestas(foroViewModel: ForoViewModel, skybirdDAO: SkybirdDAO){

    val pregunta = foroViewModel.preguntaSeleccionada.value
    val creador = foroViewModel.obtenerCreador(skybirdDAO)
    //Obtenemos todas las respuestas almacenadas en la base de datos para esa pregunta
    var listaRespuestas = foroViewModel.obtenerRespuestas(skybirdDAO).collectAsState(initial = emptyList()).value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.CenterHorizontally)
                .background(color = Color(0xFFADD8E6)),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = (pregunta?.titulo ?: "No hay título"),
                fontSize = 30.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = (pregunta?.contenido ?: "No hay contenido"),
                fontSize = 18.sp
            )
            Text(
                text = "- $creador -",
                fontSize = 18.sp,
                color = Color.Gray
            )
        }

        if (listaRespuestas.isEmpty()) {
            Text(
                text = "Todavía no hay respuestas",
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
                listaRespuestas.reversed().forEach { respuesta ->
                    RespuestaItem(respuesta, foroViewModel)
                }
            }
        }
    }
}

@Composable
fun RespuestaItem(answer: Answers, foroViewModel: ForoViewModel){
    val timestamp = System.currentTimeMillis()
    val diferenciaTiempo = timestamp - answer.fechaCreacion
    val tiempoFormateado = foroViewModel.formatearTiempoTranscurrido(diferenciaTiempo)

    Column(Modifier.padding(20.dp)
        .fillMaxWidth()
        .background(color = Color(0xFFADD8E6)),
        verticalArrangement = Arrangement.spacedBy(12.dp)) {

        Text(
            text = answer.contenido + "\n- " + tiempoFormateado,
            fontSize = 18.sp,
            modifier = Modifier.padding(8.dp)
        )
    }

}



