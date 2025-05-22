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
import org.jsoup.Jsoup

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
                pajaroActual.value?.let { cargarDetallesHTML(it.name) }
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

    fun cargarDetallesHTML(nombreCientifico: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.wikipediaHtmlApi.getHtml(nombreCientifico)
                if (response.isSuccessful) {
                    val html = response.body()?.string()
                    html?.let {
                        val secciones = extraerSecciones(it)
                        alimentacionText.value = secciones.entries.find { it.key.contains("Alimentación", true) }?.value
                        habitatText.value = secciones.entries.find { it.key.contains("Hábitat", true) }?.value
                        comportamientoText.value = secciones.entries.find { it.key.contains("Comportamiento", true) }?.value
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


   //Devuelve un mapa con el nombre de la sección encontrada en el html y su contenido
    fun extraerSecciones(html: String): Map<String, String> {
        //Parseamos el html para poder recorrerlo
        val doc = Jsoup.parse(html)
        val secciones = mutableMapOf<String, String>()
       //Le indicamos qué tipo de encabezados vamos a buscar en el html
        val encabezados = doc.select("h2, h3")

       //Recorremos todos los encabezados h2 y h3 que se encuentren en el html
        for (i in encabezados.indices) {
            val encabezado = encabezados[i]
            val titulo = encabezado.text()

            //Si el encabezado tiene alguno de estos títulos pasamos a obtener su contenido
            if (titulo.contains("Hábitat", true) || titulo.contains("Alimentación", true) || titulo.contains("Comportamiento", true)) {
                val contenido = StringBuilder()
                var siguiente = encabezado.nextElementSibling()

                //Recogemos el contenido hasta llegar al siguiente encabezado o que no haya más texto
                while (siguiente != null && !siguiente.tagName().startsWith("h2") && !siguiente.tagName().startsWith("h3")) {
                    if (siguiente.tagName() == "p") {
                        contenido.append(siguiente.text()).append("\n\n")
                    }
                    siguiente = siguiente.nextElementSibling()
                }
                //Almacena en el mapa el titulo del encabezado y su contenido
                secciones[titulo] = contenido.toString().trim()
            }
        }

        return secciones
    }


}