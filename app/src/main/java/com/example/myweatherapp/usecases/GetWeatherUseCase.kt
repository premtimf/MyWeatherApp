package com.example.myweatherapp.usecases

import android.util.Log
import com.example.myweatherapp.repositories.LocationRepository
import com.example.myweatherapp.repositories.WeatherRepository
import com.example.myweatherapp.roomdb.entities.City
import com.example.myweatherapp.roomdb.entities.CityWithWeatherSlots
import com.example.myweatherapp.roomdb.entities.WeatherSlot
import com.example.myweatherapp.ui.models.WeatherState
import com.example.myweatherapp.utils.SharedPreferencesUtils
import kotlinx.coroutines.flow.Flow
import java.text.DecimalFormat
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val locationRepository: LocationRepository,
    private val weatherRepository: WeatherRepository
) {

    suspend fun getWeatherForLastLocationForTheWeek(): WeatherState {
        val df = DecimalFormat("####0.00")
        val location = locationRepository.getLocation()
        val lat = df.format(location?.latitude).toDouble()
        val lon = df.format(location?.longitude).toDouble()
        val appId = SharedPreferencesUtils.getAppId()

        val response = weatherRepository.getWeather(lat = lat, lon = lon, appId = appId)
        if (response is WeatherState.Success) {
            Log.d("Premtajm", "Success on get weather $response")
            mapRestWeatherToSaveLocally(response)
        }
        return response

    }

    private suspend fun mapRestWeatherToSaveLocally(response: WeatherState.Success) {
        response.weather?.let {
            val city = City.mapFrom(it.city)
            val weatherSlots = WeatherSlot.getWeatherSlots(response.weather)
            weatherRepository.updateCity(city, weatherSlots)
        }
    }

    fun getWeatherFromLocalDb(): Flow<CityWithWeatherSlots?> {
        return weatherRepository.getWeatherFromLocalDb()
    }

}
