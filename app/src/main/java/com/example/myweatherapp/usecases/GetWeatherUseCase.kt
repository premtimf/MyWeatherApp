package com.example.myweatherapp.usecases

import android.util.Log
import com.example.myweatherapp.repositories.LocationRepository
import com.example.myweatherapp.repositories.WeatherRepository
import com.example.myweatherapp.ui.models.WeatherState
import com.example.myweatherapp.utils.SharedPreferencesUtils
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

        return weatherRepository.getWeather(lat = lat, lon = lon, appId = appId)

    }

}
