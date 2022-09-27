package com.apolloagriculture.android.takehomeassignment.ui.viewmodel

import com.apolloagriculture.android.takehomeassignment.data.repository.WeatherRepository
import com.apolloagriculture.android.takehomeassignment.data.repository.WeatherRepositoryImpl
import com.google.gson.Gson
import com.network.data.api.WeatherAPI
import com.network.data.models.DayAfterTomorrow
import com.network.data.models.Today
import com.network.data.models.Tomorrow
import com.network.data.models.WeatherResponse
import com.network.network.APIResource
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.*
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
internal class MainViewModelTest {

    // Viewmodel test to test whether the weather data is being fetched from the repository

    private lateinit var viewModel: MainViewModel
    private lateinit var weatherAPI: WeatherAPI
    private val dispatcher = StandardTestDispatcher()

    private lateinit var repository: WeatherRepository
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        mockWebServer = MockWebServer()
        mockWebServer.start()
        weatherAPI = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .build()
            .create(WeatherAPI::class.java)
        repository = WeatherRepositoryImpl(weatherAPI)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun fetchWeather() = runBlocking {
        viewModel = MainViewModel(repository)

        val weatherData = WeatherResponse(
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

        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(weatherData))
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = repository.mockWeatherData()
        assertEquals(APIResource.Success(weatherData), actualResponse)
    }
}