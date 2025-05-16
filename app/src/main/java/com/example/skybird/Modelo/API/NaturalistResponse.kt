package com.example.skybird.Modelo.API


//Recoge la respuesta que nos devuelve la api, en este caso una lista de pájaros
data class InatResponse(
    val results: List<Bird>
)

//Cada pájaro tiene nombre, nombre común y link a la wikipedia de donde sacaremos el resto de la información
data class Bird(
    val name: String,
    val common_name: String?,
    val wikipedia_url: String?,
    val default_photo: DefaultPhoto?
)

data class DefaultPhoto(
    val square_url: String,
    val medium_url: String
)