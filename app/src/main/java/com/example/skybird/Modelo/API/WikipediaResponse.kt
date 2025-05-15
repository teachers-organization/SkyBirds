package com.example.skybird.Modelo.API

//La respuesta de esta api nos devuelve detalles de la wikipedia sobre cada pájaro
//A veces podemos obtener una imágen
data class WikiSummary(
    val title: String,
    val extract: String,
    val thumbnail: WikiImage?
)

data class WikiImage(
    val source: String
)
