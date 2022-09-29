package com.example.mobileup.models

import com.google.gson.annotations.SerializedName

data class Coin(
    @SerializedName("id") val id : String,
    @SerializedName("symbol") val symbol : String,
    @SerializedName("name") val name : String,
    @SerializedName("image") val logo : String,
    @SerializedName("current_price") val price : Double,
    @SerializedName("price_change_percentage_24h") val priceChange : Double
)
