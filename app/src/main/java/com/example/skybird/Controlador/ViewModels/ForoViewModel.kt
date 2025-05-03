package com.example.skybird.Controlador.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skybird.Data.BBDD.Questions
import com.example.skybird.Data.BBDD.SkybirdDAO
import com.example.skybird.Data.BBDD.Users
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ForoViewModel: ViewModel() {

    fun crearPregunta(skybirdDAO: SkybirdDAO,
                      titulo: String,
                      contenido: String,
                      usuario: Users,
                      onResultado: (Boolean) -> Unit
                      )
    {

        val pregunta = Questions(0, titulo, contenido, , usuario.id)
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