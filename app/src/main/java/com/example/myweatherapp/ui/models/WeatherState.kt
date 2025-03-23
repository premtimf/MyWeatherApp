package com.example.myweatherapp.ui.models

import com.example.myweatherapp.rest.models.WeatherForecastResponse

sealed class WeatherState {

    object Initial : WeatherState()
    object Loading: WeatherState()

    data class Success(
        val weather: WeatherForecastResponse? = null
    ): WeatherState()

    data class Error(
        val message: String,
        val exception: Exception
    ): WeatherState()


}
