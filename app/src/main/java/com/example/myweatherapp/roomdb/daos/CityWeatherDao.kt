package com.example.myweatherapp.roomdb.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.myweatherapp.roomdb.entities.City
import com.example.myweatherapp.roomdb.entities.CityWithWeatherSlots
import com.example.myweatherapp.roomdb.entities.WeatherSlot
import kotlinx.coroutines.flow.Flow

/**
 * Created by PremtimFarizi on 23/Mar/2025
 **/
@Dao
interface CityWeatherDao {


    //We know we're going to have only one city
    @Query("SELECT * FROM city")
    fun getCity(): Flow<CityWithWeatherSlots?>

    @Query("SELECT * FROM weatherslot")
    fun getWeatherSlots(): Flow<List<WeatherSlot>>

    @Insert
    suspend fun insertAll(vararg cities: City)

    @Insert
    suspend fun insert(city: City)

    @Insert
    suspend fun insertWeatherSlots(weatherSlots: List<WeatherSlot>)

    @Query("DELETE FROM city")
    suspend fun deleteAllCities()

    @Query("DELETE FROM weatherslot")
    suspend fun deleteAllWeatherSlots()

    @Transaction
    suspend fun deleteAndInsert(city: City) {
        deleteAllCities()
        insert(city)
    }

    @Transaction
    suspend fun updateCityWeatherData(
        city: City,
        weatherSlots: List<WeatherSlot>
    ) {
        deleteAllWeatherSlots()
        deleteAllCities()
        insert(city)
        insertWeatherSlots(weatherSlots)
    }



}