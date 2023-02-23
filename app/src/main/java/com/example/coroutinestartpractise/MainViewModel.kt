package com.example.coroutinestartpractise

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.concurrent.thread

class MainViewModel : ViewModel() {

    private val parentJob = Job()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d("MainViewModel", "Exception caught: $throwable")
    }
    private val coroutineScope = CoroutineScope(Dispatchers.Main + parentJob + exceptionHandler)


    fun method() {
        val childJob1 = coroutineScope.launch {
            delay(3000)
            Log.d("MainViewModel", "first coroutine finished")
        }
        val childJob2 = coroutineScope.launch {
            delay(2000)
            Log.d("MainViewModel", "second coroutine finished")
        }
        val childJob3 = coroutineScope.launch {
            delay(1000)
            error()
            Log.d("MainViewModel", "third coroutine finished")
        }
    }

    private fun error() {
        throw RuntimeException()
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }
}