package com.example.myweatherapp.repositories

import com.example.myweatherapp.ui.models.WeatherState

interface WeatherRepository {

    suspend fun getWeather(lat: Double?, lon: Double?, appId: String?): WeatherState

}
