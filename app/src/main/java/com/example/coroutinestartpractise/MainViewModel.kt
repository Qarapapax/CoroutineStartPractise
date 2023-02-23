package com.example.coroutinestartpractise

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.concurrent.thread

class MainViewModel : ViewModel() {

    private val parentJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + parentJob)


    fun method() {
        val childJob1 = coroutineScope.launch {
            delay(3000)
            Log.d("MainViewModel", "first coroutine finished")
        }

        val childJob2 = coroutineScope.launch {
            delay(2000)
            childJob1.cancel()
            Log.d("MainViewModel", "second coroutine finished")
            Log.d("MainViewModel", "parent coroutine cancelled: ${parentJob.isCancelled}")
        }

    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }
}