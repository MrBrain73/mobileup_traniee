package com.example.mobileup.data.repository

import com.example.mobileup.data.api.RetrofitService
import com.example.mobileup.models.InfoCoin
import com.google.gson.JsonArray
import retrofit2.Response

class Repository {
    suspend fun getAllCoin(vs_currency : String, per_page : Int) : Response<JsonArray> =
        RetrofitService.getRetrofitService().getAllCoin(vs_currency, per_page)

    suspend fun getInfoAboutCoin(id : String) : Response<InfoCoin> =
        RetrofitService.getRetrofitService().getInfoAboutCoin(id)
}