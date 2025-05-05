package com.example.skybird.View

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skybird.Controlador.ViewModels.ForoViewModel
import com.example.skybird.Controlador.ViewModels.SesionViewModel
import com.example.skybird.Data.BBDD.Questions
import com.example.skybird.Data.BBDD.SkybirdDAO

@Composable
fun Foro(skybirdDAO: SkybirdDAO, sesionViewModel: SesionViewModel, volver: () -> Unit, pregunta: () -> Unit, foroViewModel: ForoViewModel){

    val scrollState = rememberScrollState()
    val a = remember { mutableStateOf("") }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 60.dp)
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

            Text(
                text = "Foro",
                fontSize = 35.sp,
                color = Color.Black,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 16.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFADD8E6))
                    .padding(10.dp)
                    .verticalScroll(scrollState)
            ) {
                MostrarDudas(skybirdDAO, foroViewModel)
            }
        }

        Button(
            onClick = { pregunta() },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF5A7391),
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
fun MostrarDudas(skybirdDAO: SkybirdDAO, foroViewModel: ForoViewModel, navDetPregunta: () -> Unit){
    //Obtenemos todas las dudas almacenadas en la base de datos
    var listaPreguntas = foroViewModel.obtenerPreguntas(skybirdDAO).collectAsState(initial = emptyList()).value

    LazyVerticalGrid(
        columns = GridCells.Fixed(2), //Esto crea dos columnas
        modifier = Modifier.fillMaxSize()
            .padding(16.dp)
    ) {
        items(listaPreguntas) { pregunta ->
            PreguntaItem(pregunta, navDetPregunta, foroViewModel)
        }
    }
}

@Composable
fun PreguntaItem(question: Questions, navDetPregunta: () -> Unit, foroViewModel: ForoViewModel){

}






