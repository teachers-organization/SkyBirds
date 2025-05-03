package com.example.skybird.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skybird.Controlador.ViewModels.SesionViewModel
import com.example.skybird.R

@Composable
fun Home(sesionViewModel: SesionViewModel, config: () -> Unit, foro: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        //Menú superior
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFFADD8E6))
                .padding(top = 40.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
                .align(Alignment.TopCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = R.drawable.user),
                contentDescription = "User Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )

            Text(
                text = sesionViewModel.usuarioActual.value?.nick ?: "Sin nombre",
                fontSize = 25.sp,
                color = Color.Black,
                modifier = Modifier.padding(start = 8.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.configuracion),
                contentDescription = "Botón de Configuración",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .clickable { config() }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            Button(
                onClick = { /* Acción 1 */ },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5A7391),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(80.dp)
            ) {
                Text(text = "Diccionario de aves",
                    fontSize = 23.sp)
            }

            Button(
                onClick = { foro() },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5A7391),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(80.dp)
            ) {
                Text(text = "Foro",
                    fontSize = 23.sp)
            }

            Button(
                onClick = { /* Acción 3 */ },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5A7391),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(80.dp)
            ) {
                Text(text = "Avistamientos",
                    fontSize = 23.sp)
            }
        }
    }
}