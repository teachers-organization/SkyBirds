package com.example.skybird.View

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import com.example.skybird.Controlador.ViewModels.ForoViewModel
import com.example.skybird.Controlador.ViewModels.SesionViewModel
import com.example.skybird.Modelo.BBDD.Answers
import com.example.skybird.Modelo.BBDD.SkybirdDAO
import kotlinx.coroutines.flow.firstOrNull

@Composable
fun MostrarPregunta(skybirdDAO: SkybirdDAO, volver: () -> Unit, foroViewModel: ForoViewModel, sesionViewModel: SesionViewModel, responder: () -> Unit) {

    val esAutor = foroViewModel.esAutorPregunta(sesionViewModel)

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
                    containerColor = Color(0xFFA3B18A),
                    contentColor = Color.White
                ),
                modifier = Modifier.align(Alignment.Start)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color.White,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { volver() }
                )
            }

            MostrarDudaYRespuestas(foroViewModel, skybirdDAO, sesionViewModel)

        }
        Button(
            onClick = { responder() },
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
        if (esAutor || sesionViewModel.usuarioActual.value!!.admin){
            Button(
                onClick = {
                    foroViewModel.borrarPregunta(skybirdDAO)
                    volver()
                },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFBC4749),
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
fun MostrarDudaYRespuestas(foroViewModel: ForoViewModel, skybirdDAO: SkybirdDAO, sesionViewModel: SesionViewModel){

    val pregunta = foroViewModel.preguntaSeleccionada.value
    val creador = foroViewModel.obtenerCreador(skybirdDAO)
    //Obtenemos todas las respuestas almacenadas en la base de datos para esa pregunta
    var listaRespuestas = foroViewModel.obtenerRespuestas(skybirdDAO).collectAsState(initial = emptyList()).value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
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
                    text = pregunta?.titulo ?: "Sin título",
                    fontSize = 24.sp,
                    color = Color(0xFF1A2C47)
                )
                Text(
                    text = pregunta?.contenido ?: "Sin contenido",
                    fontSize = 16.sp,
                    color = Color(0xFF2E2E2E)
                )
                Text(
                    text = "- $creador -",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 16.dp),
            thickness = 1.dp,
            color = Color.LightGray
        )

        Text(
            text = "Respuestas",
            fontSize = 20.sp,
            color = Color(0xFF5A7391),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 8.dp)
        )

        HorizontalDivider(
            modifier = Modifier.padding(bottom = 16.dp),
            thickness = 1.dp,
            color = Color.LightGray
        )

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
                    .padding(top = 10.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                listaRespuestas.reversed().forEach { respuesta ->
                    RespuestaItem(respuesta, foroViewModel, skybirdDAO, sesionViewModel)
                }
            }
        }
    }
}

@Composable
fun RespuestaItem(answer: Answers, foroViewModel: ForoViewModel, skybirdDAO: SkybirdDAO, sesionViewModel: SesionViewModel){
    val creador = remember { mutableStateOf("Cargando...") }
    val esAutor = foroViewModel.esAutorRespuesta(sesionViewModel, answer)

    LaunchedEffect(answer.userId) {
        val usuario = skybirdDAO.getUserById(answer.userId).firstOrNull()
        creador.value = usuario?.nick ?: "Usuario desconocido"
    }

    val timestamp = System.currentTimeMillis()
    val diferenciaTiempo = timestamp - answer.fechaCreacion
    val tiempoFormateado = foroViewModel.formatearTiempoTranscurrido(diferenciaTiempo)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .shadow(4.dp, shape = RoundedCornerShape(12.dp))
            .background(Color(0xFFEAF4F4), shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Column(
            Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = answer.contenido,
                fontSize = 16.sp,
                color = Color(0xFF2E2E2E)
            )
            Text(
                text = creador.value,
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.End)
            )
            Text(
                text = tiempoFormateado,
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.End)
            )
            if (esAutor || sesionViewModel.usuarioActual.value!!.admin){
                Button(
                    onClick = {
                        foroViewModel.borrarRespuesta(skybirdDAO, answer)
                    },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFBC4749),
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .padding(3.dp)
                ) {
                    Text(text = "Borrar",
                        fontSize = 15.sp)
                }
            }
        }
    }
}



