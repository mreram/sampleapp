package com.eram.backbase.main.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.eram.backbase.datasource.local.LocalDataSource
import com.eram.backbase.model.City
import com.eram.backbase.test.dispatchers.TestDispatcherProvider
import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class MainViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val localDataSource: LocalDataSource = mock()
    private val application: Application = mock()
    private val viewModel = MainViewModel(localDataSource, TestDispatcherProvider(), application)
    private lateinit var citiesData: List<City>

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        citiesData = readCitiesDataFromResources("cities.json")
    }

    @Test
    fun `onCreated called with a large data of cities`() {
        val observer = mock<Observer<List<City>>>()
        val argumentCaptor = argumentCaptor<List<City>>()
        whenever(localDataSource.getCities(application)).thenReturn(citiesData)
        viewModel.displayLiveData.observeForever(observer)
        viewModel.onCreated()
        verify(observer, times(1)).onChanged(argumentCaptor.capture())
    }

    @Test
    fun `onQuerySubmit called with a sample query`() {
        val observer = mock<Observer<List<City>>>()
        val argumentCaptor = argumentCaptor<List<City>>()
        whenever(localDataSource.getCities(application)).thenReturn(citiesData)
        viewModel.displayLiveData.observeForever(observer)
        viewModel.onCreated()
        viewModel.onQuerySubmit("Iran")
        verify(observer, times(2)).onChanged(argumentCaptor.capture())
    }

    @Test
    fun `onQuerySubmit called with an empty query`() {
        val observer = mock<Observer<List<City>>>()
        whenever(localDataSource.getCities(application)).thenReturn(citiesData)
        viewModel.displayLiveData.observeForever(observer)
        viewModel.onQuerySubmit("")
        verifyZeroInteractions(observer)
    }

    @Test
    fun `onQuerySubmit called when initial data is empty`() {
        val observer = mock<Observer<List<City>>>()
        whenever(localDataSource.getCities(application)).thenReturn(citiesData)
        viewModel.displayLiveData.observeForever(observer)
        viewModel.onQuerySubmit("")
        verifyZeroInteractions(observer)
    }

    @Throws(IOException::class)
    fun readCitiesDataFromResources(fileName: String): List<City> {
        var inputStream: InputStream? = null
        try {
            inputStream = javaClass.classLoader?.getResourceAsStream(fileName)
            val reader = BufferedReader(InputStreamReader(inputStream))
            return Gson().newBuilder().create().fromJson(reader, Array<City>::class.java).toList()
        } finally {
            inputStream?.close()
        }
    }
}