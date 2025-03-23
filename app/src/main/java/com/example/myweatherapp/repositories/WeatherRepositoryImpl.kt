package com.example.myweatherapp.repositories

import android.util.Log
import com.example.myweatherapp.rest.APIService
import com.example.myweatherapp.roomdb.WeatherDatabase
import com.example.myweatherapp.roomdb.entities.City
import com.example.myweatherapp.roomdb.entities.CityWithWeatherSlots
import com.example.myweatherapp.roomdb.entities.WeatherSlot
import com.example.myweatherapp.ui.models.WeatherState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: APIService,
    private val weatherDatabase: WeatherDatabase
) : WeatherRepository {

    override suspend fun getWeather(
        lat: Double?,
        lon: Double?,
        appId: String?
    ): WeatherState {

        return try {
            val response = apiService.getWeather(lat, lon, appId)
            WeatherState.Success(weather = response)
        } catch (e: Exception) {
            Log.d(TAG, "Something went wrong: ${e.message}", e)
            WeatherState.Error("Something went wrong", e)
        }
    }

    override suspend fun updateCity(
        city: City,
        weatherSlots: List<WeatherSlot>
    ) {
        weatherDatabase.cityDao().updateCityWeatherData(city, weatherSlots)
    }

    override fun getWeatherFromLocalDb(): Flow<CityWithWeatherSlots?> {
        return weatherDatabase.cityDao().getCity()
    }

    companion object {
        private const val TAG = "WeatherRepositoryImpl"
    }

}
