package com.eram.backbase.main.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eram.backbase.datasource.local.LocalDataSource
import com.eram.backbase.model.view.CityItem
import com.eram.backbase.radix.RadixTree
import com.eram.backbase.test.dispatchers.DispatcherProvider
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.concurrent.locks.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val dispatcher: DispatcherProvider,
    application: Application
) : AndroidViewModel(application) {

    private val radixTree = RadixTree<CityItem>()

    private var _displayLiveData = MutableLiveData<List<CityItem>>()
    val displayLiveData: LiveData<List<CityItem>> = _displayLiveData

    private val reentrantLock = ReentrantLock()

    fun onCreated() {
        if (_displayLiveData.value.isNullOrEmpty().not()) {
            return
        }
        viewModelScope.launch(dispatcher.io) {
            reentrantLock.lock()
            val cities = localDataSource.getCities(getApplication()).sortedBy {
                it.name + it.country
            }.map {
                CityItem(
                    it.id,
                    it.name,
                    it.country,
                    LatLng(it.coordinates.lat, it.coordinates.lon)
                )
            }
            _displayLiveData.postValue(cities)
            cities.forEach { radixTree[(it.name + ", " + it.country).lowercase()] = it }
            reentrantLock.unlock()
        }
    }

    fun onQuerySubmit(query: String) {
        if (radixTree.isNotEmpty() && reentrantLock.isLocked.not()) {
            val filteredList = radixTree.getEntriesWithPrefix(query.lowercase())
            _displayLiveData.postValue(filteredList.map { it.value })
        }
    }
}