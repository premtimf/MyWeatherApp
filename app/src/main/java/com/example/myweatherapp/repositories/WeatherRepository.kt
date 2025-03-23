package com.example.myweatherapp.repositories

import com.example.myweatherapp.roomdb.entities.City
import com.example.myweatherapp.roomdb.entities.CityWithWeatherSlots
import com.example.myweatherapp.roomdb.entities.WeatherSlot
import com.example.myweatherapp.ui.models.WeatherState
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun getWeather(lat: Double?, lon: Double?, appId: String?): WeatherState

    suspend fun updateCity(city: City, weatherSlots: List<WeatherSlot>)

    fun getWeatherFromLocalDb(): Flow<CityWithWeatherSlots?>

}
