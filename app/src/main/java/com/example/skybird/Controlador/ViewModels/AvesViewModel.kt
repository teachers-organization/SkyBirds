package com.example.skybird.Controlador.ViewModels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.skybird.Modelo.API.RetrofitClient
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import com.example.skybird.Modelo.API.Bird
import com.example.skybird.Modelo.API.WikiSummary
import com.example.skybird.Modelo.BBDD.Users

class AvesViewModel: ViewModel() {

    private val _aves = mutableStateOf<List<Bird>>(emptyList())
    val aves: State<List<Bird>> = _aves
    //Almacenar pájaro
    var pajaroActual = mutableStateOf<Bird?>(null)

    var wikiSummary = mutableStateOf<WikiSummary?>(null)
    val alimentacionText = mutableStateOf<String?>(null)
    val habitatText = mutableStateOf<String?>(null)
    val comportamientoText = mutableStateOf<String?>(null)

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

    fun cargarInfoAve() {
        viewModelScope.launch {
            try {
                val responseGeneral = pajaroActual.value?.let { RetrofitClient.wikipediaApi.getSummary(it.name) }
                wikiSummary.value = responseGeneral

                val response = pajaroActual.value?.let { it.preferred_common_name?.let { it1 ->
                    RetrofitClient.wikipediaMobileApi.getMobileSections(
                        it1
                    )
                } }
                alimentacionText.value = response?.remaining?.sections?.find {
                    (it.heading!!.contains("Alimentación", ignoreCase = true) ) || (it.heading.contains("Diet", ignoreCase = true))
                }?.text

                habitatText.value = response?.remaining?.sections?.find {
                    (it.heading!!.contains("Hábitat", ignoreCase = true)) || (it.heading.contains("Habitat", ignoreCase = true))
                }?.text

                comportamientoText.value = response?.remaining?.sections?.find {
                    (it.heading!!.contains("Comportamiento", ignoreCase = true)) || (it.heading.contains("Behavior", ignoreCase = true))
                }?.text
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("WikiMobileAPI", "Error en llamada: ${e.message}", e)
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