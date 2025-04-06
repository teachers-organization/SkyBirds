package com.example.skybird.View

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.skybird.R

@Composable
fun inicioSesion(modifier: Modifier = Modifier){

    val password = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White))
    {
        Column(modifier = Modifier
            .fillMaxSize()
            .align(alignment = Alignment.Center)
            .padding(60.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Header Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
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
                    .padding(30.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
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
                        value = password.value,
                        onValueChange = { password.value = it },
                        visualTransformation = PasswordVisualTransformation(), //Esconde la contraseña
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = { /* Lógica de envío aquí */ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF5A7391),
                            contentColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Iniciar sesión")
                    }

                    Spacer(Modifier.padding(20.dp))

                    Text(
                        text = "¿No tienes cuenta?",
                        color = Color.Black
                    )

                    Button(
                        onClick = { /* Lógica de envío aquí */ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF5A7391),
                            contentColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Crear cuenta")
                    }

                }
            }

        }


    }


}