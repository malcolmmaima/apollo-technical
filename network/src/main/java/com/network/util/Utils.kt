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
package com.network.util

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.network.network.APIResource

fun parseErrors(failure: APIResource.Error): String {
    return when {
        failure.isNetworkError -> "Network Error"
        failure.errorCode == 403 -> {
            "Unauthorized request"
        }
        failure.errorCode == 404 -> {
            ("Resource not found")
        }
        failure.errorCode == 422 -> {
            ("Validation error")
        }
        failure.errorCode == 500 -> {
            try {
                val errorBody =
                    Gson().fromJson(failure.errorBody?.string(), JsonObject::class.java)
                (errorBody.get("message").asString)
            } catch (e: Exception) {
                ("Internal server error")
            }
        }
        failure.errorCode == 504 -> {
            ("Gateway timeout")
        }
        failure.errorCode == 0 -> {
            ("Unknown error")
        }
        else -> {
            val error = failure.errorBody?.string().toString()
            (error)
        }
    }
}