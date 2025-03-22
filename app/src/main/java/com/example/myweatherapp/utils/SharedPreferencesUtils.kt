package com.example.myweatherapp.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

/**
 * Created by PremtimFarizi on 22/Mar/2025
 **/
object SharedPreferencesUtils {

    private val SHARED_PREFS_NAME = "my_weather_app_preferences"
    private val OPEN_WEATHER_API_KEY = "open_weather_api_key"

    var sharedPreferences: SharedPreferences? = null

    fun init(context: Context) {
        val masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        sharedPreferences = EncryptedSharedPreferences.create(
            SHARED_PREFS_NAME,
            masterKey,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        saveOpenWeatherApi()
    }

    private fun saveOpenWeatherApi() {
        sharedPreferences?.edit()
            ?.putString(OPEN_WEATHER_API_KEY, "d1054a6ac667c22b3ec8865716423b7e")
            ?.apply()
    }
}