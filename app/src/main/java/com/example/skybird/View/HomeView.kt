package com.example.skybird.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skybird.Controlador.ViewModels.SesionViewModel
import com.example.skybird.R

@Composable
fun Home(
    sesionViewModel: SesionViewModel,
    config: () -> Unit,
    foro: () -> Unit,
    inicioSesion: () -> Unit,
    diccionarioAves: () -> Unit,
    adminUsers: () -> Unit,
    listaAnillamientos: () -> Unit
) {

    val botonModifier = Modifier
        .fillMaxWidth()
        .height(55.dp)
        .shadow(4.dp, RoundedCornerShape(50))

    val botonColors = ButtonDefaults.buttonColors(
        containerColor = Color(0xFFA3B18A),
        contentColor = Color.White
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                //Degradado para el fondo
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFE8F0F2), Color(0xFFDCEAF5))
                )
            )

    ) {
        //Menú superior
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp)
                .background(color = Color(0xFF3A4F66))
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
                    fontSize = 30.sp,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    letterSpacing = 0.5.sp
                )

                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Configuración",
                    tint = Color.White,
                    modifier = Modifier
                        .size(45.dp)
                        .clickable { config() },
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            if (sesionViewModel.usuarioActual.value?.admin == true) {
                Button(
                    onClick = { adminUsers() },
                    shape = RoundedCornerShape(50),
                    colors = botonColors,
                    modifier = botonModifier
                ) {
                    Text(
                        text = "Administrar usuarios",
                        fontSize = 20.sp,
                        letterSpacing = 0.5.sp
                    )
                }
            }

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
                onClick = { listaAnillamientos() },
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
                onClick = {
                    sesionViewModel.cerrarSesión()
                    inicioSesion()
                },
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