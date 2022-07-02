package com.eram.backbase.model.view

import com.google.android.gms.maps.model.LatLng
import java.io.Serializable

data class CityItem(
    val id: Int,
    val name: String,
    val country: String,
    val coordinates: LatLng
) : Serializable