package com.example.skybird.Controlador.ViewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.skybird.Modelo.API.RetrofitClient
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import com.example.skybird.Modelo.API.Bird

class AvesViewModel: ViewModel() {

    private val _aves = mutableStateOf<List<Bird>>(emptyList())
    val aves: State<List<Bird>> = _aves

    private var pagina = 1
    //Esta variable sirve para no lanzar varias llamadas a la api a la vez
    //Controla si todavía hay una llamada a la api para no poder hacer otra a la vez
    private var cargando = false

    fun obtenerAves() {
        if (!cargando) {
            cargando = true
            viewModelScope.launch {
                try {
                    val response = RetrofitClient.inatApi.getBirds(perPage = 40, page = pagina)
                    _aves.value += response.results
                    println(aves.value.size)
                    pagina++
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    cargando = false
                }
            }
        }
    }

    //Función para filtrar por nombre del ave
    fun filtrarNombre(lista: List<Bird>, texto: String): List<Bird> {
        val listaFiltrada = lista.filter { pajaro ->
            val regex = Regex(".*${Regex.escape(texto)}.*", RegexOption.IGNORE_CASE)
            regex.containsMatchIn(pajaro.name)
        }
        return listaFiltrada
    }


}