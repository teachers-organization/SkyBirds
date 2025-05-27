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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.skybird.Controlador.ViewModels.AvesViewModel

@Composable
fun DetallesAve(volver: () -> Unit, avesViewModel: AvesViewModel) {

    LaunchedEffect(Unit) {
        avesViewModel.cargarInfoAve()
    }

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

            MostrarDetallesAve(avesViewModel)

        }
    }
}

@Composable
fun MostrarDetallesAve(avesViewModel: AvesViewModel) {

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
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(avesViewModel.wikiSummary.value?.thumbnail?.source)
                .crossfade(true)
                .build(),
            contentDescription = "Imagen del ave " + avesViewModel.pajaroActual.value?.preferred_common_name,
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(24.dp))

        avesViewModel.pajaroActual.value?.let {
            it.preferred_common_name?.let { it1 ->
                Text(
                    text = it1,
                    fontSize = 32.sp,
                    color = primaryGreen,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        avesViewModel.wikiSummary.value?.extract?.let {
            Section(
                title = "Descripción",
                content = it,
                titleColor = primaryGreen,
                contentColor = textSecondary
            )
        }

        avesViewModel.alimentacionText.value?.let {
            Section(
                title = "Alimentación",
                content = it,
                titleColor = secondaryGreen,
                contentColor = textSecondary
            )
        }

        avesViewModel.habitatText.value?.let {
            Section(
                title = "Hábitat",
                content = it,
                titleColor = secondaryGreen,
                contentColor = textSecondary
            )
        }

        avesViewModel.comportamientoText.value?.let {
            Section(
                title = "Comportamiento",
                content = it,
                titleColor = secondaryGreen,
                contentColor = textSecondary
            )
        }
    }
}

@Composable
fun Section(
    title: String,
    content: String,
    titleColor: Color,
    contentColor: Color,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Text(
            text = title,
            fontSize = 25.sp,
            color = titleColor,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        Surface(
            shadowElevation = 1.dp,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = content,
                fontSize = 20.sp,
                color = contentColor,
                modifier = Modifier.padding(14.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

