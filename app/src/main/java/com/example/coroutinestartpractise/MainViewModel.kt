package com.example.coroutinestartpractise

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlin.concurrent.thread

class MainViewModel : ViewModel() {

    fun method() {
        val job = viewModelScope.launch(Dispatchers.Default) {
            Log.d("MainViewModel", "Started")
            val before = System.currentTimeMillis()
            var count = 0
            for (i in 0..100_000_000) {
                for (j in 0..100) {
                    ensureActive()
                    count++
                }
            }
            Log.d("MainViewModel", "Finished ${System.currentTimeMillis() - before}")
        }
        job.invokeOnCompletion {
            Log.d("MainViewModel", "Coroutine was cancelled. $it")
        }
        viewModelScope.launch {
            delay(3000)
            job.cancel()
        }
    }
}