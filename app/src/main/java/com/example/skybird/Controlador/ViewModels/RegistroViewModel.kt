package com.example.skybird.Controlador.ViewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skybird.Data.BBDD.SkybirdDAO
import com.example.skybird.Data.BBDD.Users
import kotlinx.coroutines.launch

class RegistroViewModel : ViewModel() {

    //Almacenar usuario al iniciar sesión
    var usuarioActual = mutableStateOf<Users?>(null)

    //Comprobar si el correo existe previamente en la base de datos
    fun comprobarUsuario(
        user: Users,
        skybirdDAO: SkybirdDAO,
        onResultado: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            val userExists = skybirdDAO.getUserByEmail(user.email)
            if (userExists == null) {
                skybirdDAO.insertUser(user)
                onResultado(false)
            } else {
                onResultado(true)
            }
        }
    }


}