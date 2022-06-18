package com.eram.backbase.main.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.eram.backbase.datasource.local.LocalDataSource
import com.eram.backbase.model.City
import com.eram.backbase.radix.RadixTree
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val localDataSource: LocalDataSource,
    application: Application
) : AndroidViewModel(application) {

    private val radixTree = RadixTree<City>()

    fun onCreated() {
        viewModelScope.launch(Dispatchers.IO) {
            val cities = localDataSource.getCities(getApplication())
            cities.forEach { radixTree[(it.name + ", " + it.country).lowercase()] = it }
        }
    }

    fun onQuerySubmit(query: String) {
        radixTree.getEntriesWithPrefix(query.lowercase())
    }
}