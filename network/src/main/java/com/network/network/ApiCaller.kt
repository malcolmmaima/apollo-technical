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
package com.network.network

import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber

suspend fun <T : Any> safeApiCall(
    apiCall: suspend () -> T,
) : APIResource<T> {
    return withContext(Dispatchers.IO) {
        try {
            val response = apiCall.invoke()
            Timber.tag("BaseRepo").d("safeApiCall: %s", response)
            APIResource.Success(response)
        } catch (throwable: Throwable) {
            when(throwable){
                is HttpException -> {
                    Timber.tag("BaseRepo").e("safeApiCall: %s", throwable.message)
                    APIResource.Error(false, throwable.code(), throwable.response()?.errorBody())
                }
                else -> {
                    Timber.tag("BaseRepo").e("safeApiCall: %s", throwable.message)
                    APIResource.Error(true, null, null)
                }
            }
        }
    }
}

private fun convertErrorBody(throwable: HttpException): ErrorResponse? = try {
    throwable.response()?.errorBody()?.charStream()?.let {
        val gson = GsonBuilder()
            .create()
        gson.fromJson(it, ErrorResponse::class.java)
    }
} catch (exception: Exception) {
    Timber.e(exception)
    null
}