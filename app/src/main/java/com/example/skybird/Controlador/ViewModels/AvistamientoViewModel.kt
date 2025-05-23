package com.example.skybird.Controlador.ViewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skybird.Modelo.API.RetrofitClient.inatApiSpecies
import com.example.skybird.Modelo.BBDD.Anillamiento
import com.example.skybird.Modelo.BBDD.Especie
import com.example.skybird.Modelo.BBDD.SkybirdDAO
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class AvistamientoViewModel: ViewModel() {

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




}