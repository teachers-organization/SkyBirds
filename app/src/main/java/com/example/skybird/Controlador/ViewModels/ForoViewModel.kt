package com.example.skybird.Controlador.ViewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skybird.Modelo.BBDD.Answers
import com.example.skybird.Modelo.BBDD.Questions
import com.example.skybird.Modelo.BBDD.SkybirdDAO
import com.example.skybird.Modelo.BBDD.Users
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ForoViewModel : ViewModel() {

    //Almacenar la pregunta seleccionada
    var preguntaSeleccionada = mutableStateOf<Questions?>(null)

    fun crearPregunta(
        skybirdDAO: SkybirdDAO,
        titulo: String,
        contenido: String,
        usuario: Users,
        onResultado: (Boolean) -> Unit
    ) {

        val timestamp = System.currentTimeMillis()
        val pregunta = Questions(0, titulo, contenido, timestamp, usuario.id)
        viewModelScope.launch {
            try {
                skybirdDAO.insertQuestion(pregunta)
                onResultado(true)
            } catch (e: Exception) {
                onResultado(false)
            }
        }
    }

    fun crearRespuesta(
        skybirdDAO: SkybirdDAO,
        contenido: String,
        usuario: Users,
        onResultado: (Boolean) -> Unit
    ) {

        val timestamp = System.currentTimeMillis()
        val respuesta =
            Answers(0, contenido, timestamp, usuario.id, preguntaSeleccionada.value!!.id)

        viewModelScope.launch {
            try {
                skybirdDAO.insertAnswer(respuesta)
                onResultado(true)
            } catch (e: Exception) {
                onResultado(false)
            }
        }
    }

    var listaPreguntas = MutableStateFlow<List<Questions>>(emptyList())

    fun obtenerPreguntas(skybirdDAO: SkybirdDAO): StateFlow<List<Questions>> {
        viewModelScope.launch {
            listaPreguntas.value = skybirdDAO.getAllQuestions().first()
        }
        return listaPreguntas
    }

    var usuarioPregunta = MutableStateFlow<Users?>(null)

    fun obtenerCreador(skybirdDAO: SkybirdDAO): String {
        viewModelScope.launch {
            val usuario = skybirdDAO.getUserById(preguntaSeleccionada.value!!.userId).first()
            usuarioPregunta.value = usuario
        }
        return usuarioPregunta.value?.nick ?: "Usuario desconocido"
    }

    fun esAutorPregunta(sesionViewModel: SesionViewModel): Boolean {
        try {
            return sesionViewModel.usuarioActual.value?.id == preguntaSeleccionada.value?.userId
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    fun esAutorRespuesta(sesionViewModel: SesionViewModel, answer: Answers): Boolean {
        try {
            return sesionViewModel.usuarioActual.value?.id == answer.userId
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    fun borrarPregunta(skybirdDAO: SkybirdDAO) {
        viewModelScope.launch {
            preguntaSeleccionada.value?.let { skybirdDAO.deleteQuestion(it) }
        }
    }

    fun borrarRespuesta(skybirdDAO: SkybirdDAO, answer: Answers) {
        viewModelScope.launch {
            skybirdDAO.deleteAnswer(answer)
        }
    }


    private val _listaRespuestas = MutableStateFlow<List<Answers>>(emptyList())
    val listaRespuestas: StateFlow<List<Answers>> = _listaRespuestas

    //Función para obtener todas las respuestas
    fun obtenerRespuestas(skybirdDAO: SkybirdDAO) {
        viewModelScope.launch {
            skybirdDAO.getAnswerByIdQuestion(preguntaSeleccionada.value!!.id).collect { respuesta ->
                _listaRespuestas.value = respuesta
            }
        }
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

    //Función para filtrar por título
    fun filtrarTitulo(lista: List<Questions>, texto: String): List<Questions> {
        val listaFiltrada = lista.filter { pregunta ->
            val regex = Regex(".*${Regex.escape(texto)}.*", RegexOption.IGNORE_CASE)
            regex.containsMatchIn(pregunta.titulo)
        }
        return listaFiltrada
    }

}