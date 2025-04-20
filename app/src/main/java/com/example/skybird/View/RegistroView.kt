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
import com.example.skybird.Controlador.ViewModels.RegistroViewModel
import com.example.skybird.Data.BBDD.SkybirdDAO
import com.example.skybird.Data.BBDD.Users

@Composable
fun registro(skybirdDAO: SkybirdDAO, registroViewModel: RegistroViewModel, volver: () -> Unit){

    val nick = remember { mutableStateOf("") }
    val nombre = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val contrasenya = remember { mutableStateOf("") }
    val repetirContrasenya = remember { mutableStateOf("") }
    val isChecked = remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    var existeUsuario : Boolean = false
    var context = LocalContext.current

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
                    .padding(30.dp)
                    .verticalScroll(scrollState), //Desplazamiento para scrollear
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    Text(
                        text = "Nombre completo",
                        color = Color.Black
                    )

                    TextField(
                        value = nombre.value,
                        onValueChange = { nombre.value = it },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = "Nick",
                        color = Color.Black
                    )

                    TextField(
                        value = nick.value,
                        onValueChange = { nick.value = it },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = "Correo electrónico",
                        color = Color.Black
                    )

                    TextField(
                        value = email.value,
                        onValueChange = { email.value = it },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = "Contraseña",
                        color = Color.Black
                    )

                    TextField(
                        value = contrasenya.value,
                        onValueChange = { contrasenya.value = it },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = "Repetir contraseña",
                        color = Color.Black
                    )

                    TextField(
                        value = repetirContrasenya.value,
                        onValueChange = { repetirContrasenya.value = it },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row() {
                        Checkbox(
                            checked = isChecked.value,
                            onCheckedChange = { isChecked.value = it }
                        )

                        Text(
                            text = "Aceptar términos y condiciones",
                            color = Color.Black
                        )
                    }

                    Button(
                        onClick = {
                            //comprobar que no existe nadie con ese correo previamente
                            try {
                            existeUsuario = registroViewModel.comprobarUsuario(
                                Users(
                                    id = 0,
                                    nombreCompleto = nombre.toString(),
                                    admin = false,
                                    nick = nick.toString(),
                                    email = email.toString(),
                                    psswd = contrasenya.toString()
                                ),
                                skybirdDAO
                            )
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                                  if (existeUsuario){
                                      Toast.makeText(context, "Este correo ya está registrado", Toast.LENGTH_SHORT).show()
                                  }else{
                                      Toast.makeText(context, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show()
                                  }},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF5A7391),
                            contentColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Darme de alta")
                    }

                }
            }

        }


    }

}





