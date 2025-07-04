package com.example.skybird.Vista

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.skybird.Controlador.ViewModels.AvesViewModel
import com.example.skybird.Modelo.API.Bird

@Composable
fun Diccionario(volver: () -> Unit, navDetPajaro: () -> Unit, avesViewModel: AvesViewModel) {

    val filtrarNombre = remember {mutableStateOf("")}

    //Llamamos a obtenerAves solo una vez al entrar en la pantalla
    LaunchedEffect(Unit) {
        avesViewModel.obtenerAves()
        if(avesViewModel.avesFiltradas.value.isEmpty()){
        avesViewModel.obtenerTodas()
        }
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

            Text(
                text = "Diccionario de aves",
                fontSize = 25.sp,
                color = Color(0xFF1A2C47),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 16.dp)
            )

            Row(Modifier.fillMaxWidth()) {
                TextField(
                    value = filtrarNombre.value,
                    onValueChange = { filtrarNombre.value = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(2.dp),
                    shape = RoundedCornerShape(8.dp),
                    placeholder = { Text("Filtrar por nombre...", color = Color.Gray) }
                )
            }

            MostrarAves(navDetPajaro, filtrarNombre.value, avesViewModel)

        }
    }
}

@Composable
fun MostrarAves(
    navDetPajaro: () -> Unit,
    filtro: String,
    avesViewModel: AvesViewModel
) {

    //Observamos la variable para que cuando la corrutina termine nos devuelve la lista de aves
    val listaAves by avesViewModel.aves

    //Variable para detectar si el usuario ha llegado al final del scroll
    val gridState = rememberLazyGridState()

    if (listaAves.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.Center),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
    } else {

        //Filtramos las aves por nombre
        val listaMostrada by remember(filtro, avesViewModel.aves, avesViewModel.avesFiltradas) {
            derivedStateOf {
                if (filtro.isNotBlank()) {
                    avesViewModel.avesFiltradas.value.filter {
                        it.preferred_common_name?.contains(filtro, ignoreCase = true) == true
                    }
                } else {
                    avesViewModel.aves.value
                }
            }
        }

        //Detectar si llegó al final del scroll
        //Cuando se llegan a los 3 últimos pájaros la variable se vuelve true
        val cargarMas = remember {
            derivedStateOf {
                val lastVisible = gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
                filtro.isBlank() && lastVisible >= listaMostrada.size - 3
            }
        }

        //Cragar los siguientes elementos de la api
        LaunchedEffect(cargarMas.value) {
            if (cargarMas.value) {
                avesViewModel.obtenerAves()
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            state = gridState,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp)
        ) {
            items(listaMostrada) { ave ->
                PajaroItem(ave, navDetPajaro, avesViewModel)
            }
        }
    }
}

@Composable
fun PajaroItem(ave: Bird, navDetPajaro: () -> Unit, avesViewModel: AvesViewModel) {

    Column {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(ave.default_photo?.medium_url)
                .crossfade(true)
                .build(),
            contentDescription = "Imágen del ave " + ave.preferred_common_name,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.FillHeight
        )

        Button(
            onClick = {
                avesViewModel.pajaroActual.value = ave
                navDetPajaro()
            },
            modifier = Modifier
                .fillMaxWidth()
                .shadow(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF687054),
                contentColor = Color.White
            ),
            shape = RectangleShape,
            elevation = ButtonDefaults.elevatedButtonElevation(
                defaultElevation = 8.dp,
                pressedElevation = 10.dp
            )
        ) {
            ave.preferred_common_name?.let {
                Text(
                    text = it,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}



