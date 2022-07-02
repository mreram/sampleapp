package com.eram.backbase.model

import com.google.gson.annotations.SerializedName

class LatLong(
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val lon: Double
)