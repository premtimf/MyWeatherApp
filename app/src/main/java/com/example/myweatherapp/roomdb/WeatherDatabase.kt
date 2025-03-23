package com.example.myweatherapp.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myweatherapp.roomdb.daos.CityWeatherDao
import com.example.myweatherapp.roomdb.entities.City
import com.example.myweatherapp.roomdb.entities.WeatherSlot

/**
 * Created by PremtimFarizi on 23/Mar/2025
 **/
@Database(entities = [City::class, WeatherSlot::class], version = 1)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun cityDao(): CityWeatherDao
}