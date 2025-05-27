package com.example.skybird.View

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skybird.Controlador.ViewModels.AvistamientoViewModel

@Composable
fun DetallesAvistamiento(volver: () -> Unit, avistamientoViewModel: AvistamientoViewModel) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(top = 60.dp, bottom = 30.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
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

            MostrarDetallesAvistamiento(avistamientoViewModel)

        }
    }
}

@Composable
fun MostrarDetallesAvistamiento(avistamientoViewModel: AvistamientoViewModel) {

    val scrollState = rememberScrollState()
    val primaryGreen = Color(0xFF2C6E49)
    val secondaryGreen = Color(0xFF497E5E)
    val textSecondary = Color(0xFF545659)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        avistamientoViewModel.avistamientoSeleccionado.value?.let {
            Text(
                text = it.codigoAnillamiento,
                fontSize = 32.sp,
                color = primaryGreen,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        avistamientoViewModel.avistamientoSeleccionado.value?.let {
            Section(
                title = "Fecha de avistamiento",
                content = it.fecha,
                titleColor = primaryGreen,
                contentColor = textSecondary
            )
        }

        avistamientoViewModel.avistamientoSeleccionado.value?.let {
            Section(
                title = "Lugar de avistamiento",
                content = it.lugar,
                titleColor = primaryGreen,
                contentColor = textSecondary
            )
        }

        avistamientoViewModel.avistamientoSeleccionado.value?.let {
            it.ala?.let { it1 ->
                Section(
                    title = "Tamaño del ala el día que se avistó",
                    content = it1,
                    titleColor = primaryGreen,
                    contentColor = textSecondary
                )
            }
        }

        avistamientoViewModel.avistamientoSeleccionado.value?.let {
            it.sexo?.let { it1 ->
                Section(
                    title = "Identificación del sexo del ave",
                    content = it1,
                    titleColor = primaryGreen,
                    contentColor = textSecondary
                )
            }
        }

        avistamientoViewModel.avistamientoSeleccionado.value?.let {
            it.edad?.let { it1 ->
                Section(
                    title = "Edad aproximada del ave",
                    content = it1,
                    titleColor = primaryGreen,
                    contentColor = textSecondary
                )
            }
        }

        avistamientoViewModel.avistamientoSeleccionado.value?.let {
            it.peso?.let { it1 ->
                Section(
                    title = "Peso del ave",
                    content = it1,
                    titleColor = primaryGreen,
                    contentColor = textSecondary
                )
            }
        }

        avistamientoViewModel.avistamientoSeleccionado.value?.let {
            it.observaciones?.let { it1 ->
                Section(
                    title = "Observaciones adicionales",
                    content = it1,
                    titleColor = primaryGreen,
                    contentColor = textSecondary
                )
            }
        }

    }
}
