package com.example.skybird.Controlador.ViewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skybird.Data.BBDD.SkybirdDAO
import com.example.skybird.Data.BBDD.Users
import kotlinx.coroutines.launch

class SesionViewModel : ViewModel() {

    //Almacenar usuario al iniciar sesión
    var usuarioActual = mutableStateOf<Users?>(null)

    //Comprobar si el usuario existe en la base de datos
    //Si existe iniciamos sesión y almacenamos el usuario
    fun iniciarSesion(skybirdDAO: SkybirdDAO,
                      email: String,
                      psswd: String,
                      onResultado: (Boolean) -> Unit)
    {
        viewModelScope.launch {
            val userExists = skybirdDAO.getUserByEmailAndPassword(email, psswd)
            if (userExists == null) {
                onResultado(false)
            } else {
                usuarioActual.value = userExists
                onResultado(true)
            }
        }

    }




}