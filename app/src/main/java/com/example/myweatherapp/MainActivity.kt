package com.example.myweatherapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.example.myweatherapp.ui.models.WeatherState
import com.example.myweatherapp.ui.theme.MyWeatherAppTheme
import com.example.myweatherapp.viewmodels.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("MissingPermission")
    val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions: Map<String, @JvmSuppressWildcards Boolean> ->
            if (permissions.all { it.value }) {

                weatherViewModel.getWeather()

            } else {
                Log.d("MainActivity", "no permissions")
            }
        }

    private val weatherViewModel: WeatherViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            MyWeatherAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(Modifier.padding(innerPadding)) {
                        val weatherState = weatherViewModel.weatherState.collectAsState()
                        Greeting(
                            name = "MyWeather App",
                            modifier = Modifier
                        )
                        when (weatherState.value) {
                            is WeatherState.Success -> {
                                val cityName = (weatherState.value as WeatherState.Success).weather.city.name
                                Text(cityName)
                            }

                            is WeatherState.Error -> {
                                Text((weatherState.value as WeatherState.Error).message)
                            }
                            WeatherState.Initial -> {
                                Button(onClick = {
                                    checkForPermissionToGetData()
                                }) {
                                    Text("GetData")
                                }
                            }
                            WeatherState.Loading -> {
                                Text("Loading")
                            }
                        }
                    }
                }
            }
        }
    }

    private fun checkForPermissionToGetData() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
            return
        } else {
            weatherViewModel.getWeather()
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyWeatherAppTheme {
        Greeting("Android")
    }
}