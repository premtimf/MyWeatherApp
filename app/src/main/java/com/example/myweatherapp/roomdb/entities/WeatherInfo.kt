package com.example.myweatherapp.roomdb.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.myweatherapp.rest.models.CityModel
import com.example.myweatherapp.rest.models.WeatherDay
import com.example.myweatherapp.rest.models.WeatherForecastResponse

/**
 * Created by PremtimFarizi on 23/Mar/2025
 **/
@Entity
data class City(
    @PrimaryKey val cityId: Int,
    val name: String
) {
    companion object {
        fun mapFrom(city: CityModel): City {
            return City(cityId = city.id, name = city.name)
        }
    }
}

@Entity
data class WeatherSlot(
    @PrimaryKey val weatherSlotDt: Long,
    val dateText: String,
    val weatherCityId: Int,
    val temperature: Double,
    val minTemperature: Double,
    val maxTemperature: Double,
    val main: String?,
    val desc: String?,
    val icon: String?
) {
    companion object {
        fun getWeatherSlots(weatherResponse: WeatherForecastResponse): List<WeatherSlot> {
            val weatherSlots = mutableListOf<WeatherSlot>()
            for(weatherDay in 0..<weatherResponse.weatherDays.size step 8) {
                val weatherSlot = weatherResponse.weatherDays[weatherDay]
                weatherSlots.add(mapFrom(weatherSlot, weatherResponse.city.id))
            }
            return weatherSlots
        }

        private fun mapFrom(weatherDay: WeatherDay, cityId: Int): WeatherSlot {
            return WeatherSlot(
                weatherSlotDt = weatherDay.date,
                weatherCityId = cityId,
                temperature = weatherDay.temperature.temp,
                maxTemperature = weatherDay.temperature.max,
                minTemperature = weatherDay.temperature.min,
                main = weatherDay.weather.firstOrNull()?.main,
                desc = weatherDay.weather.firstOrNull()?.description,
                icon = weatherDay.weather.firstOrNull()?.icon,
                dateText = weatherDay.dateText
            )
        }
    }
}

data class CityWithWeatherSlots(
    @Embedded val city: City,
    @Relation(
        entity = WeatherSlot::class,
        parentColumn = "cityId",
        entityColumn = "weatherCityId"
    )
    val weatherSlots: List<WeatherSlot>
)
