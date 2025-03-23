package com.example.myweatherapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweatherapp.di.IoDispatcher
import com.example.myweatherapp.ui.models.WeatherLocalState
import com.example.myweatherapp.ui.models.WeatherState
import com.example.myweatherapp.usecases.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _weatherFromApiState = MutableStateFlow<WeatherState>(WeatherState.Initial)

    val weatherFromApiState: StateFlow<WeatherState> get() = _weatherFromApiState

    private val _weatherState = MutableStateFlow<WeatherLocalState>(WeatherLocalState.Initial)

    val weatherState: StateFlow<WeatherLocalState> get() = _weatherState

    init {
        viewModelScope.launch(ioDispatcher) {
            getWeatherUseCase.getWeatherFromLocalDb()
                .catch {
                    WeatherLocalState.Error(message = "Failed to load weather data from local db, try to update", exception = Exception(it))
                }.collect {
                    _weatherState.value = WeatherLocalState.Success(city = it)
                }
        }
    }

    fun getWeather() {

        viewModelScope.launch(ioDispatcher) {
            _weatherFromApiState.value = WeatherState.Loading

            val response = getWeatherUseCase.getWeatherForLastLocationForTheWeek()
            if (response is WeatherState.Error) {
                Log.d(TAG, "response error", response.exception)
            }
            _weatherFromApiState.value = response
        }

    }

    companion object {
        private const val TAG = "WeatherViewModel"
    }

}
