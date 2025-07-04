package com.example.skybird.Vista

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skybird.Controlador.ViewModels.SesionViewModel
import com.example.skybird.Modelo.BBDD.SkybirdDAO

@Composable
fun BorrarCuenta(
    skybirdDAO: SkybirdDAO,
    sesionViewModel: SesionViewModel,
    volver: () -> Unit,
    inicio: () -> Unit
) {

    val comprobarBorrar = remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .padding(horizontal = 20.dp, vertical = 40.dp)
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

            Text(
                text = "Configuración",
                fontSize = 35.sp,
                color = Color(0xFF1A2C47),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 16.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 50.dp)
                    .shadow(8.dp, RoundedCornerShape(16.dp))
                    .background(
                        color = Color(0xFFF0F8FF),
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxSize()
                        .padding(20.dp)
                        .verticalScroll(scrollState),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 16.dp),
                        thickness = 1.dp,
                        color = Color.LightGray
                    )

                    Text(
                        text = "Borrar cuenta",
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

                    if (!comprobarBorrar.value) {
                        Button(
                            onClick = {
                                comprobarBorrar.value = true
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFF44336),
                                contentColor = Color.White
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                "Borrar cuenta",
                                fontSize = 20.sp
                            )
                        }
                    } else {
                        Text(
                            text = "Al borrar la cuenta perderás tus datos para siempre ¿Estás seguro/a de querer borrarla?",
                            fontSize = 20.sp,
                            color = Color(0xFF5A7391),
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(bottom = 8.dp)
                        )
                        Row(Modifier.fillMaxWidth()) {
                            Button(
                                onClick = {
                                    sesionViewModel.usuarioActual.value?.let {
                                        sesionViewModel.borrarUsuario(skybirdDAO, it)
                                    }
                                    inicio()
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFF44336),
                                    contentColor = Color.White
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(2.dp)
                            ) {
                                Text(
                                    "Sí, borrar",
                                    fontSize = 20.sp
                                )
                            }
                            Button(
                                onClick = {
                                    comprobarBorrar.value = false
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFA3B18A),
                                    contentColor = Color.White
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(2.dp)
                            ) {
                                Text(
                                    "No",
                                    fontSize = 20.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}