package com.example.skybird.Modelo.API

import retrofit2.http.GET
import retrofit2.http.Query

//Hacemos la petición a la api y nos devuelve una lista de pájaros
interface InatApiService {
    @GET("v1/taxa")
    suspend fun getBirds(
        @Query("rank") rank: String = "species",
        @Query("taxon_id") taxon_id: Int = 3,
        @Query("per_page") perPage: Int = 40,
        @Query("page") page: Int = 1,
        @Query("locale") locale: String = "es"
    ): InatResponse
}

interface InatApiSpecieService {
    @GET("v1/taxa")
    suspend fun getBirdsByName(
        @Query("q") query: String,
        @Query("rank") rank: String = "species",
        @Query("taxon_id") taxonId: Int = 3,
        @Query("per_page") perPage: Int = 10,
        @Query("page") page: Int = 1,
        @Query("locale") locale: String = "es"
    ): InatResponse
}