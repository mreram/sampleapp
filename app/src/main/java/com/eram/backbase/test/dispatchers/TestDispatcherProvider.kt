package com.eram.backbase.test.dispatchers

import kotlinx.coroutines.Dispatchers

class TestDispatcherProvider : DispatcherProvider {

    override val default
        get() = Dispatchers.Unconfined
    override val io
        get() = Dispatchers.Unconfined
    override val main
        get() = Dispatchers.Main
    override val unconfined
        get() = Dispatchers.Unconfined
}