package com.example.skybird.Modelo.API

import retrofit2.http.GET
import retrofit2.http.Query

//Hacemos la petición a la api y nos devuelve una lista de pájaros
interface InatApiService {
    @GET("v1/taxa")
    suspend fun getBirds(
        @Query("q") query: String = "bird",
        @Query("rank") rank: String = "species",
        @Query("per_page") perPage: Int = 20
    ): InatResponse
}