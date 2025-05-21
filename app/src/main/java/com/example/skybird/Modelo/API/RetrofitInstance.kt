package com.example.skybird.Modelo.API

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Generamos las instancias de retrofit que se conectan a las apis
object RetrofitClient {
    val inatApi: InatApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.inaturalist.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(InatApiService::class.java)
    }

    val wikipediaApi: IWikipediaApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://es.wikipedia.org/api/rest_v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IWikipediaApiService::class.java)
    }

    val wikipediaMobileApi: IWikipediaDetalles by lazy {
        Retrofit.Builder()
            .baseUrl("https://es.wikipedia.org/api/rest_v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IWikipediaDetalles::class.java)
    }

}