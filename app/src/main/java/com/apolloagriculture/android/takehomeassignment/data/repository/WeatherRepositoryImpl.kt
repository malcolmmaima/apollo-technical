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
package com.apolloagriculture.android.takehomeassignment.data.repository

import com.network.data.models.WeatherResponse
import com.network.data.api.WeatherAPI
import com.network.data.models.DayAfterTomorrow
import com.network.data.models.Today
import com.network.data.models.Tomorrow
import com.network.network.APIResource
import com.network.network.safeApiCall
import okhttp3.ResponseBody

/**
 * @param WeatherAPI
 */
internal class WeatherRepositoryImpl(
    private val weatherAPI: WeatherAPI
) : WeatherRepository {

    override suspend fun fetchWeatherData(): APIResource<WeatherResponse> =
        safeApiCall {
            weatherAPI.fetchWeatherData()
        }

    override suspend fun mockWeatherData(): APIResource<WeatherResponse> {
        val weatherResponse = WeatherResponse(
            dayAfterTomorrow = DayAfterTomorrow(
                description = "Cloudy",
                icon = "04d",
                lowTemp = 20.0,
                highTemp = 30.0
            ),
            today = Today(
                description = "Cloudy",
                icon = "04d",
                lowTemp = 20.0,
                highTemp = 30.0
            ),
            tomorrow = Tomorrow(
                description = "Cloudy",
                icon = "04d",
                lowTemp = 20.0,
                highTemp = 30.0
            )
        )
        return APIResource.Success(weatherResponse)
    }

    override suspend fun MockFailureRepository(): APIResource<WeatherResponse> {
        return APIResource.Error(errorBody = null, errorCode = 500, isNetworkError = true)
    }
}


