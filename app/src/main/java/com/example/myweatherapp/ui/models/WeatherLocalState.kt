package com.example.myweatherapp.ui.models

import com.example.myweatherapp.roomdb.entities.CityWithWeatherSlots

sealed class WeatherLocalState {

    object Initial: WeatherLocalState()

    data class Success(
        val city: CityWithWeatherSlots?,
    ): WeatherLocalState()

    data class Error(
        val message: String,
        val exception: Exception
    ): WeatherLocalState()


}
