package com.eram.backbase.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class City(
    @SerializedName("_id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("country") val country: String,
    @SerializedName("coord") val coordinates: LatLong
) : Serializable