package com.example.myweatherapp.repositories

import com.example.myweatherapp.rest.APIService
import com.example.myweatherapp.ui.models.WeatherState
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: APIService
) : WeatherRepository {

    override suspend fun getWeather(
        lat: Double?,
        lon: Double?,
        appId: String?
    ): WeatherState {

        return try {
            val response = apiService.getWeather(lat, lon, appId)
            WeatherState.Success(response)
        } catch (e: Exception) {
            WeatherState.Error("Something went wrong", e)
        }
    }

}
