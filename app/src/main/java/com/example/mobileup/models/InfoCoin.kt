package com.example.mobileup.models

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class InfoCoin(
    @SerializedName("description") val description : JsonObject,
    @SerializedName("categories") val categories : JsonArray
)