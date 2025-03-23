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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.example.myweatherapp.ui.models.WeatherLocalState
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
                        Greeting(
                            name = "MyWeather App",
                            modifier = Modifier
                        )
                        ApiState()
                        LocalData()
                        Button(onClick = {
                            checkForPermissionToGetData()
                        }) {
                            Text("GetData")
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun ApiState() {
        val weatherState = weatherViewModel.weatherFromApiState.collectAsState()
        when (weatherState.value) {
            is WeatherState.Success -> {
                Text("Data updated successfully")
            }

            is WeatherState.Error -> {
                Text("There was something wrong while updating data")
            }
            WeatherState.Initial -> {
                Text("Click get data to update")
            }
            WeatherState.Loading -> {
                Text("Updating data from api")
            }
        }
    }

    @Composable
    private fun LocalData() {
        val weatherLocalState = weatherViewModel.weatherState.collectAsState()
        when (weatherLocalState.value) {
            is WeatherLocalState.Success -> {
                val city = (weatherLocalState.value as WeatherLocalState.Success).city

                Text(city?.city?.name ?: "City name")

                city?.weatherSlots?.let {
                    LazyColumn {
                        items(it) {
                            Row {
                                Text(it.main ?: "Weather")
                                Spacer(modifier = Modifier.size(5.dp))
                                Text("${it.temperature} °Celcius")
                                Spacer(modifier = Modifier.size(5.dp))
                                Text(it.dateText)
                            }
                        }
                    }
                }
            }

            is WeatherLocalState.Error -> {
                Text((weatherLocalState.value as WeatherLocalState.Error).message)
            }
            WeatherLocalState.Initial -> {
                Text("Refresh data")
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