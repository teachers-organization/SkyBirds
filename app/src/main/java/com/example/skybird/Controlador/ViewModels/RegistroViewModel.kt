package com.example.skybird.Controlador.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skybird.Modelo.BBDD.SkybirdDAO
import com.example.skybird.Modelo.BBDD.Users
import kotlinx.coroutines.launch

class RegistroViewModel : ViewModel() {

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

    fun esEmailValido(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}