package com.example.skybird.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skybird.Controlador.ViewModels.SesionViewModel
import com.example.skybird.R

@Composable
fun Home(sesionViewModel: SesionViewModel, config: () -> Unit, foro: () -> Unit, inicioSesion: () -> Unit, diccionarioAves: () -> Unit) {

    val botonModifier = Modifier
        .fillMaxWidth()
        .height(65.dp)
        .shadow(4.dp, RoundedCornerShape(50))

    val botonColors = ButtonDefaults.buttonColors(
        containerColor = Color(0xFF5A7391),
        contentColor = Color.White
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        //Menú superior
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp)
                .background(color = Color(0xFFA3B18A))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp, horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = "User Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                )

                Text(
                    text = sesionViewModel.usuarioActual.value?.nick ?: "Sin nombre",
                    fontSize = 24.sp,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    letterSpacing = 0.5.sp
                )

                Image(
                    painter = painterResource(id = R.drawable.configuracion),
                    contentDescription = "Botón de Configuración",
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .clickable { config() }
                        .background(Color.White)
                        .padding(12.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { diccionarioAves() },
                shape = RoundedCornerShape(50),
                colors = botonColors,
                modifier = botonModifier
            ) {
                Text(
                    text = "Diccionario de aves",
                    fontSize = 20.sp,
                    letterSpacing = 0.5.sp
                )
            }

            Button(
                onClick = { foro() },
                shape = RoundedCornerShape(50),
                colors = botonColors,
                modifier = botonModifier
            ) {
                Text(
                    text = "Foro",
                    fontSize = 20.sp,
                    letterSpacing = 0.5.sp
                )
            }

            Button(
                onClick = { /* Acción 3 */ },
                shape = RoundedCornerShape(50),
                colors = botonColors,
                modifier = botonModifier
            ) {
                Text(
                    text = "Avistamientos",
                    fontSize = 20.sp,
                    letterSpacing = 0.5.sp
                )
            }

            Button(
                onClick = { sesionViewModel.cerrarSesión()
                          inicioSesion()},
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFBC4749),
                    contentColor = Color.White
                ),
                modifier = botonModifier
            ) {
                Text(
                    text = "Cerrar sesión",
                    fontSize = 20.sp,
                    letterSpacing = 0.5.sp
                )
            }

        }
    }
}