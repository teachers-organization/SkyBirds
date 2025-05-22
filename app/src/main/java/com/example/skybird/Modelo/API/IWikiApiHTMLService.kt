package com.example.skybird.Modelo.API

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//Obtener el html con toda la información
interface IWikiApiHTMLService {
    @GET("page/{title}/html")
    suspend fun getHtml(
        @Path("title") title: String,
        @Query("redirect") redirect: String = "no"
    ): Response<ResponseBody>
}