package com.example.skybird.Controlador.ViewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.skybird.Data.BBDD.Answers
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

    //Almacenar la pregunta seleccionada
    var preguntaSeleccionada = mutableStateOf<Questions?>(null)

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

    fun crearRespuesta(skybirdDAO: SkybirdDAO,
                       contenido: String,
                       usuario: Users,
                       onResultado: (Boolean) -> Unit
                       ){

        val timestamp = System.currentTimeMillis()
        val respuesta = Answers(0, contenido, timestamp, usuario.id, preguntaSeleccionada.value!!.id)

        viewModelScope.launch {
            try {
                skybirdDAO.insertAnswer(respuesta)
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

    var usuarioPregunta = MutableStateFlow<Users?>(null)

    fun obtenerCreador(skybirdDAO: SkybirdDAO):String{
        viewModelScope.launch {
            val usuario = skybirdDAO.getUserById(preguntaSeleccionada.value!!.userId).first()
            usuarioPregunta.value = usuario
        }
        return usuarioPregunta.value?.nick ?: "Usuario desconocido"
    }

    fun esAutor(sesionViewModel: SesionViewModel):Boolean{
        try {
            if (sesionViewModel.usuarioActual.value?.id == preguntaSeleccionada.value?.userId){
                return true
            }else{
                return false
            }
        }catch (e: Exception){
            e.printStackTrace()
            return false
        }
    }

    fun borrarPregunta(skybirdDAO: SkybirdDAO){
        viewModelScope.launch {
            preguntaSeleccionada.value?.let { skybirdDAO.deleteQuestion(it) }
        }
    }

    var listaRespuestas = MutableStateFlow<List<Answers>>(emptyList())

    fun obtenerRespuestas(skybirdDAO: SkybirdDAO): StateFlow<List<Answers>>{
        viewModelScope.launch {
            listaRespuestas.value = skybirdDAO.getAnswerByIdQuestion(preguntaSeleccionada.value!!.id).first()
        }
        return listaRespuestas
    }

    fun formatearTiempoTranscurrido(millis: Long): String {
        val segundos = millis / 1000
        val minutos = segundos / 60
        val horas = minutos / 60
        val dias = horas / 24

        return when {
            dias > 0 -> "hace $dias día${if (dias > 1) "s" else ""}"
            horas > 0 -> "hace $horas hora${if (horas > 1) "s" else ""}"
            minutos > 0 -> "hace $minutos minuto${if (minutos > 1) "s" else ""}"
            else -> "hace unos segundos"
        }
    }

}