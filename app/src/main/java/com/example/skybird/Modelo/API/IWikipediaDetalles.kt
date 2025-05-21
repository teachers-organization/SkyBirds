package com.example.skybird.Modelo.API

import retrofit2.http.GET
import retrofit2.http.Path

interface IWikipediaDetalles {
        //Detalles del ave
        @GET("page/mobile-sections/{title}")
        suspend fun getMobileSections(
            @Path("title") title: String
        ): WikiMobileResponse

}