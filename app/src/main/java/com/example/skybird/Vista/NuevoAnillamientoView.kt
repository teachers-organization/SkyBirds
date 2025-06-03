package com.example.skybird.Vista

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.skybird.Controlador.ViewModels.SesionViewModel
import com.example.skybird.Modelo.BBDD.Anillamiento
import com.example.skybird.Modelo.BBDD.SkybirdDAO

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevoAnillamiento(
    skybirdDAO: SkybirdDAO,
    avistamientoViewModel: AvistamientoViewModel,
    volver: () -> Unit,
    sesionViewModel: SesionViewModel
) {

    val fecha = remember { mutableStateOf("") }
    val lugar = remember { mutableStateOf("") }
    val codigoAnilla = remember { mutableStateOf("") }
    val sexo = remember { mutableStateOf<String?>(null) }
    val edad = remember { mutableStateOf<String?>(null) }
    val longitud_ala = remember { mutableStateOf<String?>(null) }
    val peso = remember { mutableStateOf<String?>(null) }
    val observaciones = remember { mutableStateOf<String?>(null) }
    val especie = remember { mutableStateOf("") }

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
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color.White,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { volver() }
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Nuevo anillamiento",
                fontSize = 25.sp,
                color = Color(0xFF1A2C47),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 16.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 20.dp)
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

                    especie.value = Desplegable(avistamientoViewModel)

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

                    val selectedIndex = remember { mutableIntStateOf(0) }
                    val options = listOf("nulo", "M", "F")

                    SingleChoiceSegmentedButtonRow {
                        options.forEachIndexed { index, label ->
                            SegmentedButton(
                                shape = SegmentedButtonDefaults.itemShape(
                                    index = index,
                                    count = options.size
                                ),
                                onClick = { selectedIndex.intValue = index
                                            sexo.value = label},
                                selected = index == selectedIndex.intValue,
                                colors = SegmentedButtonDefaults.colors(
                                    activeContainerColor = Color(0xFFA3B18A),
                                    activeContentColor = Color.White,
                                    inactiveContainerColor = Color.LightGray,
                                    inactiveContentColor = Color.DarkGray,
                                ),
                                label = { Text(label) },
                            )
                        }
                    }

                    Text(
                        text = "Edad (años)",
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
                        text = "Longitud del ala (cm)",
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
                        text = "Peso (gramos)",
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
                                    especie,
                                    fecha,
                                    lugar,
                                    codigoAnilla
                                ).any { it.value.isBlank() }
                            ) {
                                Toast.makeText(
                                    context,
                                    "Los campos con asterisco son OBLIGATORIOS",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (!avistamientoViewModel.esFechaValida(fecha.value)) {
                                Toast.makeText(
                                    context,
                                    "Formato o fecha incorrecto",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                var numEspecie = 0
                                avistamientoViewModel.crearEspecie(
                                    especie.value,
                                    skybirdDAO
                                ) { id ->
                                    numEspecie = id
                                    avistamientoViewModel.comprobarAnillamiento(
                                        Anillamiento(
                                            codigoAnillamiento = codigoAnilla.value,
                                            numEspecie,
                                            fecha = fecha.value,
                                            lugar = lugar.value,
                                            edad = edad.value,
                                            sexo = sexo.value,
                                            peso = peso.value,
                                            ala = longitud_ala.value,
                                            observaciones = observaciones.value,
                                            userId = sesionViewModel.usuarioActual.value!!.id
                                        ),
                                        skybirdDAO
                                    ) { yaExiste ->
                                        if (yaExiste) {
                                            Toast.makeText(
                                                context,
                                                "Esta anilla ya ha sido asignada",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        } else {
                                            Toast.makeText(
                                                context,
                                                "Anillamiento registrado correctamente",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
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
                            "Registrar",
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Desplegable(avistamientoViewModel: AvistamientoViewModel): String {
    val especie = remember { mutableStateOf("") }
    val expanded = remember { mutableStateOf(false) }
    val especiesSugeridas = avistamientoViewModel.especies.value
    val especieInput = especie.value

    Text(
        buildAnnotatedString {
            append("Especie")
            withStyle(style = SpanStyle(color = Color.Red)) {
                append(" *")
            }
        },
        color = Color(0xFF5A7391),
        fontSize = 20.sp
    )

    Column {
        TextField(
            value = especieInput,
            onValueChange = {
                especie.value = it
                if (it.isNotEmpty()) {
                    avistamientoViewModel.buscarEspecies(it)
                    expanded.value = true
                } else {
                    expanded.value = false
                }
            },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            placeholder = { Text("Nombre de la especie...", color = Color.Gray) }
        )

        if (expanded.value && especiesSugeridas.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .shadow(2.dp, shape = RoundedCornerShape(4.dp))
            ) {
                especiesSugeridas.take(5).forEach { sugerencia ->
                    Text(
                        text = sugerencia,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                            .background(Color(0xFFF5F5F5))
                            .clickable {
                                especie.value = sugerencia
                                expanded.value = false
                            },
                        color = Color.Black

                    )
                }
            }
        }
    }
    return especie.value
}



