/*
 * Copyright 2022 ApolloWeather
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.apolloagriculture.android.takehomeassignment.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apolloagriculture.android.takehomeassignment.data.repository.WeatherRepository
import com.network.data.models.WeatherResponse
import com.network.network.APIResource
import com.network.util.parseErrors
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    private val _mutableWeatherData = MutableSharedFlow<WeatherResponse>()
    val weatherData = _mutableWeatherData.asSharedFlow()

    private val _errorMessage = MutableSharedFlow<String>()
    val errorMessage = _errorMessage.asSharedFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asSharedFlow()

    fun getWeatherData()  = viewModelScope.launch {
        when (val result = weatherRepository.fetchWeatherData()) {
            is APIResource.Loading -> {
                _loading.value = true
            }
            is APIResource.Error -> {
                _errorMessage.emit(parseErrors(result))
                _loading.value = false
            }
            is APIResource.Success -> {
                _mutableWeatherData.emit(result.value)
                _loading.value = false
            }
        }
    }
}