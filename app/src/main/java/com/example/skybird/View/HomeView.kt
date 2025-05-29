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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
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
        .height(60.dp)
        .shadow(2.dp, RoundedCornerShape(8.dp))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
    ) {

        //Logo superior
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Header Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(300.dp))
            )
        }

        //Botones de opciones
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (sesionViewModel.usuarioActual.value?.admin == true) {
                ModernButton("Administrar usuarios", onClick = adminUsers)
            }
            ModernButton("Diccionario de aves", onClick = diccionarioAves)
            ModernButton("Foro", onClick = foro)
            ModernButton("Avistamientos", onClick = listaAnillamientos)

            Button(
                onClick = {
                    sesionViewModel.cerrarSesión()
                    inicioSesion()
                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFBC4749),
                    contentColor = Color.White
                ),
                modifier = botonModifier
            ) {
                Text(
                    text = "Cerrar sesión",
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Cerrar sesión",
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        //Header inferior
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(color = Color(0xFFA3B18A))
                .padding(start = 25.dp, end = 25.dp, top = 16.dp, bottom = 30.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = "User Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(12.dp))
                )

                Text(
                    text = sesionViewModel.usuarioActual.value?.nick ?: "Sin nombre",
                    fontSize = 25.sp,
                    color = Color.White
                )

                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Configuración",
                    tint = Color.White,
                    modifier = Modifier
                        .size(50.dp)
                        .clickable { config() }
                )
            }
        }
    }
}

@Composable
fun ModernButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFDAE1D5),
            contentColor = Color(0xFF333333)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .shadow(2.dp, RoundedCornerShape(8.dp))
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Ir a $text",
            modifier = Modifier.size(18.dp)
        )
    }
}
