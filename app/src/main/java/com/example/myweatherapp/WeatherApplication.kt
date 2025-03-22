package com.example.myweatherapp

import android.app.Application
import com.example.myweatherapp.utils.SharedPreferencesUtils

/**
 * Created by PremtimFarizi on 22/Mar/2025
 **/
class WeatherApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        SharedPreferencesUtils.init(this)
    }
}