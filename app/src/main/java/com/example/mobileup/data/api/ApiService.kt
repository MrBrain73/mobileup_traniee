package com.example.mobileup.data.api


import com.example.mobileup.models.InfoCoin
import com.google.gson.JsonArray
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("api/v3/coins/markets")
    suspend fun getAllCoin(@Query("vs_currency") vs_currency : String,
                           @Query("per_page") per_page : Int) : Response<JsonArray>

    @GET("api/v3/coins/{id}")
    suspend fun getInfoAboutCoin(@Path("id") id : String) : Response<InfoCoin>
}