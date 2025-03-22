package com.example.myweatherapp.repositories

import android.location.Location

fun interface LocationRepository {

    suspend fun getLocation(): Location?

}
