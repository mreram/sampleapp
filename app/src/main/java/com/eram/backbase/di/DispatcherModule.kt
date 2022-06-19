package com.eram.backbase.di

import com.eram.backbase.test.dispatchers.DefaultDispatcherProvider
import com.eram.backbase.test.dispatchers.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

    @Provides
    fun provideDispatcher(): DispatcherProvider = DefaultDispatcherProvider()
}