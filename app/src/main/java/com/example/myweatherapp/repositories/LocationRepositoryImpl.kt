package com.example.myweatherapp.repositories

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient
): LocationRepository {

    @SuppressLint("MissingPermission")
    override suspend fun getLocation(): Location? {
        return try {
            fusedLocationProviderClient.lastLocation.await()
        } catch (e: Exception) {
            Log.d("LocationRepository", "something went wrong", e)
            null
        }
    }

}
