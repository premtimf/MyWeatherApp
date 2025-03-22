package com.example.myweatherapp.rest.models

import com.google.gson.annotations.SerializedName

data class WeatherForecastResponse(
    @SerializedName("city")
    val city: City,
    @SerializedName("list")
    val weatherDays: List<WeatherDay>
)

data class WeatherDay(
    @SerializedName("dt")
    val long: Long,
    @SerializedName("temp")
    val temperature: Temperature,
    @SerializedName("weather")
    val weather: List<Weather>
)

data class Weather(
    @SerializedName("main")
    val main: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String
)

data class Temperature(
    @SerializedName("min")
    val min: Int,
    @SerializedName("max")
    val max: Int
)

data class City (
    @SerializedName("name")
    val name: String
)
