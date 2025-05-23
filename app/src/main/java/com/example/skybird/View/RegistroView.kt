package com.example.skybird.View

import android.widget.Toast
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
import androidx.compose.ui.unit.sp
import com.example.skybird.Controlador.ViewModels.RegistroViewModel
import com.example.skybird.Modelo.BBDD.SkybirdDAO
import com.example.skybird.Modelo.BBDD.Users

@Composable
fun Registro(skybirdDAO: SkybirdDAO, registroViewModel: RegistroViewModel, volver: () -> Unit){

    val nick = remember { mutableStateOf("") }
    val nombre = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val contrasenya = remember { mutableStateOf("") }
    val repetirContrasenya = remember { mutableStateOf("") }
    val isChecked = remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
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
                Text("Volver")
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Registro",
                fontSize = 35.sp,
                color = Color(0xFF1A2C47),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 16.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 100.dp)
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

                    Text(
                        text = "Nombre completo",
                        color = Color(0xFF5A7391),
                        fontSize = 20.sp
                    )
                    TextField(
                        value = nombre.value,
                        onValueChange = { nombre.value = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        placeholder = { Text("Introduce tu nombre completo...", color = Color.Gray) }
                    )

                    Text(
                        text = "Nick",
                        color = Color(0xFF5A7391),
                        fontSize = 20.sp
                    )
                    TextField(
                        value = nick.value,
                        onValueChange = { nick.value = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        placeholder = { Text("Introduce tu nick...", color = Color.Gray) }
                    )

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
                        placeholder = { Text("Introduce tu correo electrónico...", color = Color.Gray) }
                    )

                    Text(
                        text = "Contraseña",
                        color = Color(0xFF5A7391),
                        fontSize = 20.sp
                    )
                    TextField(
                        value = contrasenya.value,
                        onValueChange = { contrasenya.value = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        placeholder = { Text("Introduce la contraseña...", color = Color.Gray) }
                    )

                    Text(
                        text = "Repetir contraseña",
                        color = Color(0xFF5A7391),
                        fontSize = 20.sp
                    )
                    TextField(
                        value = repetirContrasenya.value,
                        onValueChange = { repetirContrasenya.value = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        placeholder = { Text("Repite la contraseña...", color = Color.Gray) }
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { isChecked.value = !isChecked.value }
                    ) {
                        Checkbox(
                            checked = isChecked.value,
                            onCheckedChange = { isChecked.value = it }
                        )
                        Text("Aceptar términos y condiciones", color = Color.Black)
                    }

                    Button(
                        onClick = {
                            //Recorre la lista de los campos comprobando que no hay ninguno vacío
                            //Si algún campo está vacío muestra la advertencia
                            if(listOf(nombre, nick, email, contrasenya, repetirContrasenya).any { it.value.isBlank() }) {
                                Toast.makeText(
                                    context,
                                    "Por favor, rellene todos los campos",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }else if (!registroViewModel.esEmailValido(email.value)){
                                Toast.makeText(context, "Correo electrónico no válido", Toast.LENGTH_SHORT).show()
                            }else if (contrasenya.value.length < 5){
                                Toast.makeText(context, "La contraseña debe contener al menos 5 caracteres", Toast.LENGTH_SHORT).show()
                            }else if (contrasenya.value != repetirContrasenya.value){
                                Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                            }else if (!isChecked.value){
                                Toast.makeText(context, "Debe aceptar los términos y condiciones", Toast.LENGTH_SHORT).show()
                            }else{
                                registroViewModel.comprobarUsuario(
                                    Users(
                                        id = 0,
                                        nombreCompleto = nombre.value,
                                        admin = false,
                                        nick = nick.value,
                                        email = email.value,
                                        psswd = contrasenya.value
                                    ),
                                    skybirdDAO
                                ){ yaExiste ->
                                 if (yaExiste){
                                        Toast.makeText(context, "Este correo ya existe", Toast.LENGTH_SHORT).show()
                                }else{
                                    Toast.makeText(context, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show()
                                }
                            }
                            }},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFA3B18A),
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






