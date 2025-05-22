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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skybird.Controlador.ViewModels.AvesViewModel
import com.example.skybird.Controlador.ViewModels.AvistamientoViewModel
import com.example.skybird.Controlador.ViewModels.RegistroViewModel
import com.example.skybird.Modelo.BBDD.SkybirdDAO
import com.example.skybird.Modelo.BBDD.Users

@Composable
fun NuevoAnillamiento(skybirdDAO: SkybirdDAO, avesViewModel: AvesViewModel, avistamientoViewModel: AvistamientoViewModel, volver: () -> Unit){

    val especie = remember { mutableStateOf("") }
    val fecha = remember { mutableStateOf("") }
    val lugar = remember { mutableStateOf("") }
    val codigoAnilla = remember { mutableStateOf("") }
    val sexo = remember { mutableStateOf("") }
    val edad = remember { mutableStateOf("") }
    val longitud_ala = remember { mutableStateOf("") }
    val peso = remember { mutableStateOf("") }
    val observaciones = remember { mutableStateOf("") }

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
                        buildAnnotatedString {
                            append("Código de la anilla")
                            withStyle(style = SpanStyle(color = Color.Red)) {
                                append(" *")
                            }
                        },
                        color = Color(0xFF5A7391),
                        fontSize = 20.sp
                    )
                    TextField(
                        value = codigoAnilla.value,
                        onValueChange = { codigoAnilla.value = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        placeholder = { Text("Código de la anilla...", color = Color.Gray) }
                    )

                    Text(
                        buildAnnotatedString {
                            append("Fecha")
                            withStyle(style = SpanStyle(color = Color.Red)) {
                                append(" *")
                            }
                        },
                        color = Color(0xFF5A7391),
                        fontSize = 20.sp
                    )
                    TextField(
                        value = fecha.value,
                        onValueChange = { fecha.value = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        placeholder = { Text("Fecha...", color = Color.Gray) }
                    )

                    Text(
                        buildAnnotatedString {
                            append("Lugar")
                            withStyle(style = SpanStyle(color = Color.Red)) {
                                append(" *")
                            }
                        },
                        color = Color(0xFF5A7391),
                        fontSize = 20.sp
                    )
                    TextField(
                        value = lugar.value,
                        onValueChange = { lugar.value = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        placeholder = { Text("Lugar...", color = Color.Gray) }
                    )

                    Text(
                        text = "Sexo",
                        color = Color(0xFF5A7391),
                        fontSize = 20.sp
                    )
                    TextField(
                        value = sexo.value,
                        onValueChange = { sexo.value = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        placeholder = { Text("Sexo...", color = Color.Gray) }
                    )

                    Text(
                        text = "Edad",
                        color = Color(0xFF5A7391),
                        fontSize = 20.sp
                    )
                    TextField(
                        value = edad.value,
                        onValueChange = { edad.value = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        placeholder = { Text("Edad...", color = Color.Gray) }
                    )

                    Text(
                        text = "Longitud del ala",
                        color = Color(0xFF5A7391),
                        fontSize = 20.sp
                    )
                    TextField(
                        value = longitud_ala.value,
                        onValueChange = { longitud_ala.value = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        placeholder = { Text("Longitud del ala...", color = Color.Gray) }
                    )

                    Text(
                        text = "Peso",
                        color = Color(0xFF5A7391),
                        fontSize = 20.sp
                    )
                    TextField(
                        value = peso.value,
                        onValueChange = { peso.value = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        placeholder = { Text("Peso...", color = Color.Gray) }
                    )

                    Text(
                        text = "Observaciones",
                        color = Color(0xFF5A7391),
                        fontSize = 20.sp
                    )
                    TextField(
                        value = observaciones.value,
                        onValueChange = { observaciones.value = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        placeholder = { Text("Observaciones...", color = Color.Gray) }
                    )

                    Button(
                        onClick = {
                            //Recorre la lista de los campos comprobando que no hay ninguno vacío
                            //Si algún campo está vacío muestra la advertencia
                            if(listOf(especie, fecha, lugar, codigoAnilla).any { it.value.isBlank() }) {
                                Toast.makeText(
                                    context,
                                    "Los campos con asterisco son OBLIGATORIOS",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }else if (){
                                Toast.makeText(context, "Correo electrónico no válido", Toast.LENGTH_SHORT).show()
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






