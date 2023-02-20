package com.example.coroutinestartpractise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.coroutinestartpractise.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.buttonLoad.setOnClickListener { loadData() }
    }

    private fun loadData() {
        Log.d("MainActivity", "Load started : $this")
        binding.progress.isVisible = true
        binding.buttonLoad.isEnabled = false
        loadCity {
            binding.tvLocation.text = it
            loadTemperature(it) {
                binding.tvTemperature.text = it.toString()
                binding.progress.isVisible = false
                binding.buttonLoad.isEnabled = true
                Log.d("MainActivity", "Load finished : $this")
            }
        }
    }

    private fun loadCity(callback: (String) -> Unit) {
        thread {
            Thread.sleep(5000)
            runOnUiThread {
                callback.invoke("Moscow")
            }
        }
    }

    private fun loadTemperature(city: String, callback: (Int) -> Unit) {
        thread {
            runOnUiThread {
                Toast.makeText(
                    this, "Loading temperature for city: $city", Toast.LENGTH_SHORT
                ).show()
            }
            Thread.sleep(5000)
            runOnUiThread { callback.invoke(17) }
        }
    }
}