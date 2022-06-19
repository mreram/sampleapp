package com.eram.backbase.test.dispatchers

import kotlinx.coroutines.Dispatchers

interface DispatcherProvider {

    val main
        get() = Dispatchers.Main
    val default
        get() = Dispatchers.Default
    val io
        get() = Dispatchers.IO
    val unconfined
        get() = Dispatchers.Unconfined
}

class DefaultDispatcherProvider : DispatcherProvider