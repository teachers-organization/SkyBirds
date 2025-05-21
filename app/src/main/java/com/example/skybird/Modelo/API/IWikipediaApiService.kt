package com.example.skybird.Modelo.API

import retrofit2.http.GET
import retrofit2.http.Path

//Petición a la api pasándole el nombre del pájaro para que nos devuelva un resumen breve
interface IWikipediaApiService {
    @GET("page/summary/{title}")
    suspend fun getSummary(
        @Path("title") title: String
    ): WikiSummary

}