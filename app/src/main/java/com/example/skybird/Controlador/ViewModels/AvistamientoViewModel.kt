package com.example.skybird.Controlador.ViewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skybird.Modelo.API.RetrofitClient.inatApiSpecies
import com.example.skybird.Modelo.BBDD.Anillamiento
import com.example.skybird.Modelo.BBDD.Especie
import com.example.skybird.Modelo.BBDD.Questions
import com.example.skybird.Modelo.BBDD.SkybirdDAO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class AvistamientoViewModel: ViewModel() {

    //Almacenar la anilla seleccionada
    var anillaSeleccionada = mutableStateOf<Anillamiento?>(null)

    var listaAnillas = MutableStateFlow<List<Anillamiento>>(emptyList())

    fun obtenerAnillas(skybirdDAO: SkybirdDAO): StateFlow<List<Anillamiento>> {
        viewModelScope.launch {
            listaAnillas.value = skybirdDAO.getAllAnillamientos().first()
        }
        return listaAnillas
    }

    //Función para filtrar por anilla
    fun filtrarAnillamientos(lista: List<Anillamiento>, texto: String): List<Anillamiento> {
        val listaFiltrada = lista.filter { anilla ->
            val regex = Regex(".*${Regex.escape(texto)}.*", RegexOption.IGNORE_CASE)
            regex.containsMatchIn(anilla.codigoAnillamiento)
        }
        return listaFiltrada
    }

    //Comprobar si la anilla ya existe en la base de datos
    fun comprobarAnillamiento(
        anillamiento: Anillamiento,
        skybirdDAO: SkybirdDAO,
        onResultado: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            val anillaExists = skybirdDAO.getAnillamientoByCodigo(anillamiento.codigoAnillamiento).firstOrNull()
            if (anillaExists == null) {
                skybirdDAO.insertAnillamiento(anillamiento)
                onResultado(false)
            } else {
                onResultado(true)
            }
        }
    }

    var numId = mutableStateOf<Int?>(null)

    //Crear especie
    fun crearEspecie(
        sugerencia: String,
        skybirdDAO: SkybirdDAO,
        onResult: (Int) -> Unit
    ) {
        viewModelScope.launch {
            val especieEncontrada = skybirdDAO.getEspecieByName(sugerencia).firstOrNull()
            val id: Int
            if (especieEncontrada == null) {
                val nuevaEspecie = Especie(nombre = sugerencia)
                skybirdDAO.insertEspecie(nuevaEspecie)
                val especieInsertada = skybirdDAO.getEspecieByName(sugerencia).firstOrNull()
                id = especieInsertada?.id ?: 0
            } else {
                id = especieEncontrada.id
            }
            onResult(id)
        }
    }

    val especies = mutableStateOf<List<String>>(emptyList())
    val isLoading = mutableStateOf(false)

    fun buscarEspecies(nombre: String) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val resultado = inatApiSpecies.getBirdsByName(query = nombre)
                especies.value = resultado.results.map { it.preferred_common_name ?: it.name }
            } catch (e: Exception) {
                especies.value = emptyList()
            } finally {
                isLoading.value = false
            }
        }
    }

    fun esFechaValida(fecha: String): Boolean {
        val formato = "dd/MM/yyyy"
        return try {
            val formatoFecha = SimpleDateFormat(formato, Locale.getDefault())
            formatoFecha.isLenient = false //Comprueba que la fecha existe en un calendario real
            formatoFecha.parse(fecha)
            true
        } catch (e: Exception) {
            false
        }
    }

}