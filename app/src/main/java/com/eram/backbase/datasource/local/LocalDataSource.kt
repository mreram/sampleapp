package com.eram.backbase.datasource.local

import android.content.Context
import com.eram.backbase.R
import com.eram.backbase.model.City
import com.eram.backbase.test.Mockable
import com.google.gson.Gson
import javax.inject.Inject

@Mockable
class LocalDataSource @Inject constructor() {

    fun getCities(context: Context): List<City> {
        val assetsReader = context.resources.openRawResource(R.raw.cities).bufferedReader()
        return Gson().newBuilder().create().fromJson(assetsReader, Array<City>::class.java).toList()
    }
}