package com.example.skybird.Controlador.ViewModels

import androidx.compose.runtime.MutableState
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

    fun obtenerAves() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.inatApi.getBirds()
                _aves.value = response.results
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }



}