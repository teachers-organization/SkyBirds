package com.example.skybird.View

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skybird.Controlador.ViewModels.SesionViewModel
import com.example.skybird.Modelo.BBDD.SkybirdDAO
import com.example.skybird.R

@Composable
fun InicioSesion(skybirdDAO: SkybirdDAO, sesionViewModel: SesionViewModel, crearCuenta: () -> Unit, login: () -> Unit, modifier: Modifier = Modifier){

    val password = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        sesionViewModel.crearAdmin(skybirdDAO)
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFF5F5F5))
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .padding(top = 60.dp, bottom = 20.dp, start = 30.dp, end = 30.dp)
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Header Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(1000.dp))
            )

            Spacer(modifier = Modifier.height(40.dp))

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 100.dp)
                    .shadow(8.dp, RoundedCornerShape(16.dp))
                    .background(Color(0xFFF0F8FF), shape = RoundedCornerShape(16.dp))
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxSize()
                        .padding(20.dp)
                        .verticalScroll(scrollState),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Text(
                        text = "Correo electrónico",
                        color = Color(0xFF5A7391),
                        fontSize = 20.sp
                    )

                    TextField(
                        value = email.value,
                        onValueChange = { email.value = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        placeholder = {
                            Text("Introduce tu correo electrónico...", color = Color.Gray)
                        }
                    )

                    Text(
                        text = "Contraseña",
                        color = Color(0xFF5A7391),
                        fontSize = 20.sp
                    )

                    TextField(
                        value = password.value,
                        onValueChange = { password.value = it },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        placeholder = {
                            Text("Introduce tu contraseña...", color = Color.Gray)
                        }
                    )

                    Button(
                        onClick = {
                            //Recorre la lista de los campos comprobando que no hay ninguno vacío
                            //Si algún campo está vacío muestra la advertencia
                            if(listOf(password, email).any { it.value.isBlank() }){
                                Toast.makeText(context, "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show()
                            }else{
                                sesionViewModel.iniciarSesion(
                                    skybirdDAO,
                                    email.value,
                                    password.value
                                ){ yaExiste ->
                                    if (yaExiste){
                                        login()
                                    }else{
                                        Toast.makeText(context, "Email o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }  },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFA3B18A),
                            contentColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Iniciar sesión")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "¿No tienes cuenta?",
                        color = Color(0xFF5A7391),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Button(
                        onClick = { crearCuenta() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFA3B18A),
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