package com.example.skybird.Controlador.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skybird.Modelo.BBDD.Anillamiento
import com.example.skybird.Modelo.BBDD.SkybirdDAO
import kotlinx.coroutines.launch

class AvistamientoViewModel: ViewModel() {

    //Comprobar si la anilla ya existe en la base de datos
    fun comprobarAnillamiento(
        anillamiento: Anillamiento,
        skybirdDAO: SkybirdDAO,
        onResultado: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            val anillaExists = skybirdDAO.getAnillamientoByCodigo(anillamiento.codigoAnillamiento)
            if (anillaExists == null) {
                skybirdDAO.insertAnillamiento(anillamiento)
                onResultado(false)
            } else {
                onResultado(true)
            }
        }
    }






}