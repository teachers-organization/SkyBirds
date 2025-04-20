package com.example.skybird.Controlador.ViewModels

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skybird.Data.BBDD.SkybirdDAO
import com.example.skybird.Data.BBDD.Users
import kotlinx.coroutines.launch

class RegistroViewModel() : ViewModel() {

    //Almacenar usuario al iniciar sesión
    var usuarioActual = mutableStateOf<Users?>(null)

    //Crear nuevo usuario
    fun altaUsuario(user: Users, skybirdDAO: SkybirdDAO) {
        viewModelScope.launch {
            skybirdDAO.insertUser(user)
        }
    }

    //Comprobar si el usuario existe previamente en la base de datos
    fun comprobarUsuario(user: Users, skybirdDAO: SkybirdDAO): Boolean {
        var userExists: Users? = null
        viewModelScope.launch {
            userExists = skybirdDAO.getUserByEmail(user.email)
        }
        if (userExists == null) {
            altaUsuario(user, skybirdDAO)
            return false
        } else {
            return true
        }
    }


}