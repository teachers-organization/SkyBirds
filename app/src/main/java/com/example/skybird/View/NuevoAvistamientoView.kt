package com.example.skybird.View

import android.widget.Toast
import androidx.compose.foundation.background
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
import com.example.skybird.Controlador.ViewModels.AvistamientoViewModel
import com.example.skybird.Modelo.BBDD.Avistamiento
import com.example.skybird.Modelo.BBDD.SkybirdDAO

@Composable
fun NuevoAvistamiento(
    skybirdDAO: SkybirdDAO,
    avistamientoViewModel: AvistamientoViewModel,
    volver: () -> Unit
) {

    val fecha = remember { mutableStateOf("") }
    val lugar = remember { mutableStateOf("") }
    val sexo = remember { mutableStateOf<String?>(null) }
    val edad = remember { mutableStateOf<String?>(null) }
    val longitud_ala = remember { mutableStateOf<String?>(null) }
    val peso = remember { mutableStateOf<String?>(null) }
    val observaciones = remember { mutableStateOf<String?>(null) }

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
                text = "Datos avistamiento",
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
                        buildAnnotatedString {
                            append("Fecha (dd/MM/yyyy)")
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
                        placeholder = { Text("Fecha(dd/MM/yyyy)...", color = Color.Gray) }
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

                    Row {
                        Button(
                            onClick = { sexo.value = "F" },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFA3B18A),
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .weight(0.75f)
                                .padding(2.dp)
                        ) {
                            Text("F")
                        }
                        Button(
                            onClick = { sexo.value = "M" },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFA3B18A),
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .weight(0.75f)
                                .padding(2.dp)
                        ) {
                            Text("M")
                        }
                        Button(
                            onClick = { sexo.value = null },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFA3B18A),
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .weight(1.5f)
                                .padding(2.dp)
                        ) {
                            Text("Indeterminado")
                        }
                    }

                    Text(
                        text = "Edad",
                        color = Color(0xFF5A7391),
                        fontSize = 20.sp
                    )
                    TextField(
                        value = edad.value ?: "",
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
                        value = longitud_ala.value ?: "",
                        onValueChange = { longitud_ala.value = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        placeholder = { Text("Longitud ala...", color = Color.Gray) }
                    )

                    Text(
                        text = "Peso",
                        color = Color(0xFF5A7391),
                        fontSize = 20.sp
                    )
                    TextField(
                        value = peso.value ?: "",
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
                        value = observaciones.value ?: "",
                        onValueChange = { observaciones.value = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        placeholder = { Text("Observaciones...", color = Color.Gray) }
                    )

                    Button(
                        onClick = {
                            //Recorre la lista de los campos comprobando que no hay ninguno vacío
                            //Si algún campo está vacío muestra la advertencia
                            if (listOf(
                                    fecha,
                                    lugar
                                ).any { it.value.isBlank() }
                            ) {
                                Toast.makeText(
                                    context,
                                    "Los campos con asterisco son OBLIGATORIOS",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }else if(!avistamientoViewModel.esFechaValida(fecha.value)){
                                Toast.makeText(
                                    context,
                                    "Formato o fecha incorrecto",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                avistamientoViewModel.crearAvistamiento(
                                    Avistamiento(0,
                                        avistamientoViewModel.anillaSeleccionada.value!!.codigoAnillamiento,
                                        fecha.value,
                                        lugar.value,
                                        edad.value,
                                        sexo.value,
                                        peso.value,
                                        longitud_ala.value,
                                        observaciones.value,
                                        avistamientoViewModel.anillaSeleccionada.value!!.userId
                                    ),
                                    skybirdDAO
                                ){ creado ->
                                    if (creado) {
                                        Toast.makeText(
                                            context,
                                            "Avistamiento registrado correctamente",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Error al registrar avistamiento",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFA3B18A),
                            contentColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Registrar")
                    }
                }
            }
        }
    }
}





