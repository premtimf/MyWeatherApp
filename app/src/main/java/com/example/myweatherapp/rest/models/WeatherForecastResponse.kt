package com.example.myweatherapp.rest.models

import com.google.gson.annotations.SerializedName

data class WeatherForecastResponse(
    @SerializedName("city")
    val city: CityModel,
    @SerializedName("list")
    val weatherDays: List<WeatherDay>
)

data class WeatherDay(
    @SerializedName("dt")
    val date: Long,
    @SerializedName("dt_txt")
    val dateText: String,
    @SerializedName("main")
    val temperature: Temperature,
    @SerializedName("weather")
    val weather: List<WeatherModel>
)

data class WeatherModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String
)

data class Temperature(
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("temp_min")
    val min: Double,
    @SerializedName("temp_max")
    val max: Double
)

data class CityModel (
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
