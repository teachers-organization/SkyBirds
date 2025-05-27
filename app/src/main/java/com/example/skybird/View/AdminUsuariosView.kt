package com.example.skybird.View

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skybird.Controlador.ViewModels.SesionViewModel
import com.example.skybird.Modelo.BBDD.SkybirdDAO
import com.example.skybird.Modelo.BBDD.Users

@Composable
fun AdminUsuarios(volver: () -> Unit, skybirdDAO: SkybirdDAO, sesionViewModel: SesionViewModel){

    val filtrarNombre = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        sesionViewModel.obtenerUsuarios(skybirdDAO)
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
                text = "Administración de usuarios",
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
                    modifier = Modifier.weight(1f)
                        .padding(2.dp),
                    shape = RoundedCornerShape(8.dp),
                    placeholder = { Text("Filtrar por nombre...", color = Color.Gray) }
                )
            }

            Spacer(Modifier.padding(10.dp))

            MostrarUsuarios(skybirdDAO, filtrarNombre.value, sesionViewModel)

        }
    }
}

@Composable
fun MostrarUsuarios(skybirdDAO: SkybirdDAO, filtrarNombre: String, sesionViewModel: SesionViewModel) {
    var listaUsuarios = sesionViewModel.listaUsuarios.collectAsState().value

    if (filtrarNombre != ""){
        listaUsuarios = sesionViewModel.filtrarNombre(listaUsuarios, filtrarNombre)
    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        listaUsuarios.forEach { usuario ->
            UsuarioItem(usuario, sesionViewModel, skybirdDAO)
        }
    }
}

@Composable
fun UsuarioItem(users: Users, sesionViewModel: SesionViewModel, skybirdDAO: SkybirdDAO){

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Nombre completo: " + users.nombreCompleto,
                    fontSize = 20.sp,
                    color = Color(0xFF5A7391),
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                )
                Text(
                    text = "Nick: " + users.nick,
                    fontSize = 20.sp,
                    color = Color(0xFF5A7391),
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                )
                Text(
                    text = "Email: " + users.email,
                    fontSize = 20.sp,
                    color = Color(0xFF5A7391),
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                )
            }
            if (!users.admin) {
                Button(
                    onClick = { sesionViewModel.borrarUsuario(skybirdDAO, users) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF44336),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.padding(2.dp)
                ) {
                    Text("Borrar")
                }
            }
        }
    }
}