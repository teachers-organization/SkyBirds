package com.example.skybird.View

import android.widget.Toast
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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skybird.Controlador.ViewModels.SesionViewModel
import com.example.skybird.Modelo.BBDD.SkybirdDAO

@Composable
fun CambioContraseña(
    skybirdDAO: SkybirdDAO,
    sesionViewModel: SesionViewModel,
    volver: () -> Unit
) {

    val contrasenyaActual = remember { mutableStateOf("") }
    val nuevaContrasenya = remember { mutableStateOf("") }
    val repetirContrasenya = remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val context = LocalContext.current

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
                        text = "Cambiar contraseña",
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

                    Text(
                        text = "Contraseña actual",
                        color = Color(0xFF5A7391),
                        fontSize = 20.sp
                    )

                    TextField(
                        value = contrasenyaActual.value,
                        onValueChange = { contrasenyaActual.value = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        placeholder = {
                            Text(
                                "Introduce tu contraseña actual...",
                                color = Color.Gray
                            )
                        }
                    )

                    Text(
                        text = "Nueva contraseña",
                        color = Color(0xFF5A7391),
                        fontSize = 20.sp
                    )

                    TextField(
                        value = nuevaContrasenya.value,
                        onValueChange = { nuevaContrasenya.value = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        placeholder = {
                            Text(
                                "Introduce tu nueva contraseña...",
                                color = Color.Gray
                            )
                        }
                    )

                    Text(
                        text = "Repetir nueva contraseña",
                        color = Color(0xFF5A7391),
                        fontSize = 20.sp
                    )

                    TextField(
                        value = repetirContrasenya.value,
                        onValueChange = { repetirContrasenya.value = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        placeholder = { Text("Repite tu nueva contraseña...", color = Color.Gray) }
                    )

                    Button(
                        onClick = {
                            if (listOf(
                                    contrasenyaActual,
                                    nuevaContrasenya,
                                    repetirContrasenya
                                ).any { it.value.isBlank() }
                            ) {
                                Toast.makeText(
                                    context,
                                    "Por favor, rellene todos los campos",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (nuevaContrasenya.value != repetirContrasenya.value) {
                                Toast.makeText(
                                    context,
                                    "No coinciden los campos de la nueva contraseña",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (nuevaContrasenya.value.length < 5) {
                                Toast.makeText(
                                    context,
                                    "Debe contener al menos 5 caracteres",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (!sesionViewModel.validarContrasenya(nuevaContrasenya.value)) {
                                Toast.makeText(
                                    context,
                                    "Debe contener al menos 1 mayúscula y 1 carácter especial !@#$%^&*()",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                sesionViewModel.cambiarContrasenya(
                                    skybirdDAO,
                                    nuevaContrasenya.value,
                                    contrasenyaActual.value
                                )
                                { actualizado ->
                                    if (actualizado) {
                                        Toast.makeText(
                                            context,
                                            "Contraseña actualizada correctamente",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "ERROR. La contraseña introducida no coincide con la actual",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                                volver()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFA3B18A),
                            contentColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "Cambiar contraseña",
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
}