package com.eram.backbase.main.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eram.backbase.datasource.local.LocalDataSource
import com.eram.backbase.model.City
import com.eram.backbase.radix.RadixTree
import com.eram.backbase.test.dispatchers.DispatcherProvider
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

    private val radixTree = RadixTree<City>()

    private var _displayLiveData = MutableLiveData<List<City>>()
    val displayLiveData: LiveData<List<City>> = _displayLiveData

    private val reentrantLock = ReentrantLock()

    fun onCreated() {
        viewModelScope.launch(dispatcher.io) {
            reentrantLock.lock()
            val cities = localDataSource.getCities(getApplication()).sortedBy {
                it.name + it.country
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