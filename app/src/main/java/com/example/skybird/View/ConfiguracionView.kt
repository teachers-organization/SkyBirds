package com.example.skybird.View

import android.widget.Toast
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
import androidx.compose.material3.Checkbox
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
import com.example.skybird.Controlador.ViewModels.SesionViewModel
import com.example.skybird.Data.BBDD.SkybirdDAO

@Composable
fun Configuracion(skybirdDAO: SkybirdDAO, sesionViewModel: SesionViewModel, volver: () -> Unit){

    val contrasenyaActual = remember { mutableStateOf("") }
    val nuevaContrasenya = remember { mutableStateOf("") }
    val repetirContrasenya = remember { mutableStateOf("") }
    val context = LocalContext.current

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White))
    {
        Column(modifier = Modifier
            .fillMaxSize()
            .align(alignment = Alignment.Center)
            .padding(60.dp)
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

            Box(modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp, top = 100.dp)
                .shadow(12.dp, RoundedCornerShape(16.dp), clip = false)
                .background(
                    color = Color(0xFFADD8E6),
                    shape = RoundedCornerShape(16.dp)
                )
            )
            {

                Column(modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize()
                    .padding(30.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    Text(
                        text = "Contraseña actual",
                        color = Color.Black
                    )

                    TextField(
                        value = contrasenyaActual.value,
                        onValueChange = { contrasenyaActual.value = it },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = "Nueva contraseña",
                        color = Color.Black
                    )

                    TextField(
                        value = nuevaContrasenya.value,
                        onValueChange = { nuevaContrasenya.value = it },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = "Repetir nueva contraseña",
                        color = Color.Black
                    )

                    TextField(
                        value = repetirContrasenya.value,
                        onValueChange = { repetirContrasenya.value = it },
                        modifier = Modifier.fillMaxWidth()
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
                            }else if (nuevaContrasenya.value.length < 5){
                                Toast.makeText(
                                    context,
                                    "La contraseña debe contener al menos 5 caracteres",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }else {
                                sesionViewModel.cambiarContrasenya(skybirdDAO, nuevaContrasenya.value, contrasenyaActual.value)
                                { actualizado ->
                                    if (actualizado){
                                        Toast.makeText(context, "Contraseña actualizada correctamente", Toast.LENGTH_SHORT).show()
                                    }else{
                                        Toast.makeText(context, "ERROR. La contraseña introducida no coincide con la actual", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                                  },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF5A7391),
                            contentColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Cambiar contraseña")
                    }

                }
            }

        }


    }

}