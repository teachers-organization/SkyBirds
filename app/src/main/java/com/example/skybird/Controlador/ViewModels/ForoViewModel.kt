package com.example.skybird.Controlador.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.skybird.Data.BBDD.Questions
import com.example.skybird.Data.BBDD.SkybirdDAO
import com.example.skybird.Data.BBDD.Users
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
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

        val timestamp = System.currentTimeMillis()
        val pregunta = Questions(0, titulo, contenido, timestamp, usuario.id)
        viewModelScope.launch {
            try {
                skybirdDAO.insertQuestion(pregunta)
                onResultado(true)
            }catch(e: Exception){
                onResultado(false)
            }
        }
    }

    var listaPreguntas = MutableStateFlow<List<Questions>>(emptyList())

    fun obtenerPreguntas(skybirdDAO: SkybirdDAO): StateFlow<List<Questions>>{
        viewModelScope.launch {
            listaPreguntas.value = skybirdDAO.getAllQuestions().first()
        }
        return listaPreguntas
    }

}