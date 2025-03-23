package com.example.myweatherapp.rest

import com.example.myweatherapp.rest.models.WeatherForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("forecast")
    suspend fun getWeather(
        @Query("lat")
        lat: Double?,
        @Query("lon")
        lon: Double?,
        @Query("appid")
        appId: String?,
        @Query("units")
        units: String = "metric"
    ): WeatherForecastResponse

}
