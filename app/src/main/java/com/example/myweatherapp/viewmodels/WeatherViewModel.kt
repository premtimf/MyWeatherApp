package com.example.myweatherapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweatherapp.di.DispatchersModule
import com.example.myweatherapp.di.IoDispatcher
import com.example.myweatherapp.ui.models.WeatherState
import com.example.myweatherapp.usecases.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _weatherState = MutableStateFlow<WeatherState>(WeatherState.Initial)

    val weatherState: StateFlow<WeatherState> get() = _weatherState

    fun getWeather() {

        viewModelScope.launch(ioDispatcher) {
            _weatherState.value = WeatherState.Loading

            val response = getWeatherUseCase.getWeatherForLastLocationForTheWeek()
            if (response is WeatherState.Error) {
                Log.d(TAG, "response error", response.exception)
            }
            _weatherState.value = response
        }
    }

    companion object {
        private const val TAG = "WeatherViewModel"
    }

}
