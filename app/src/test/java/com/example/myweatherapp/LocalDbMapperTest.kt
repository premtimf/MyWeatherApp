package com.example.myweatherapp

import com.example.myweatherapp.rest.models.CityModel
import com.example.myweatherapp.rest.models.Temperature
import com.example.myweatherapp.rest.models.WeatherDay
import com.example.myweatherapp.rest.models.WeatherForecastResponse
import com.example.myweatherapp.rest.models.WeatherModel
import com.example.myweatherapp.roomdb.entities.City
import com.example.myweatherapp.roomdb.entities.CityWithWeatherSlots
import com.example.myweatherapp.roomdb.entities.WeatherSlot
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by PremtimFarizi on 23/Mar/2025
 **/
class LocalDbMapperTest {
    @Test
    fun testMapper() {
        val apiResponse = WeatherForecastResponse(
            city = CityModel(
                id = 1,
                name = "Pristina"
            ),
            weatherDays = listOf(
                WeatherDay(
                    date = 123,
                    dateText = "2025",
                    temperature = Temperature(
                        temp = 12.0,
                        min = 10.0,
                        max = 15.5
                    ),
                    weather = listOf(
                        WeatherModel(
                            id = 123,
                            main = "Clouds",
                            description = "Cloudy time",
                            icon = "10d"
                        )
                    )
                )
            )
        )
        val cityWeatherModel = CityWithWeatherSlots(
            city = City(
                cityId = 1,
                name = "Pristina"
            ),
            weatherSlots = listOf(
                WeatherSlot(
                    weatherSlotDt = 123,
                    dateText = "2025",
                    weatherCityId = 1,
                    temperature = 12.0,
                    minTemperature = 10.0,
                    maxTemperature = 15.5,
                    main = "Clouds",
                    desc = "Cloudy time",
                    icon = "10d"
                )
            )
        )

        assertEquals(WeatherSlot.getWeatherSlots(apiResponse), cityWeatherModel.weatherSlots)
    }
}