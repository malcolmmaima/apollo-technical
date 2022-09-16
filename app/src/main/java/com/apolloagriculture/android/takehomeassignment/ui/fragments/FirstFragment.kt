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
package com.apolloagriculture.android.takehomeassignment.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.apolloagriculture.android.takehomeassignment.R
import com.apolloagriculture.android.takehomeassignment.databinding.FragmentFirstBinding
import com.apolloagriculture.android.takehomeassignment.ui.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import com.network.data.models.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val mainViewModel: MainViewModel by inject()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchWeatherData()
        initObservers()
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                mainViewModel.weatherData.collect { temperature ->
                    binding.textViewToday.text =  "${temperature.today.lowTemp} - ${temperature.today.highTemp} °C"
                    binding.textViewTodaySmall.text = "${temperature.today.description}"
                    changeIcon(temperature)

                    binding.textViewTomorrow.text = "${temperature.tomorrow.lowTemp} - ${temperature.today.highTemp} °C"
                    binding.textViewTomorrowSmall.text = "${temperature.tomorrow.description}"

                    binding.textViewAfterTomorrow.text = "${temperature.dayAfterTomorrow.lowTemp} - ${temperature.dayAfterTomorrow.highTemp} °C"
                    binding.textViewAfterTomorrowSmall.text = "${temperature.dayAfterTomorrow.description}"
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                mainViewModel.errorMessage.collectLatest {
                    Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun changeIcon(temperature: WeatherResponse) {
        //change icon for today, tomorrow and day after tomorrow
        when(temperature.today.icon){
            "CLEAR_DAY" -> binding.imageViewToday.setImageResource(R.drawable.ic_weather_clear_day)
            "SCATTERED_CLOUDS_DAY" -> binding.imageViewToday.setImageResource(R.drawable.ic_weather_some_clouds)
            "BROKEN_OVERCAST_CLOUDS_DAY" -> binding.imageViewToday.setImageResource(R.drawable.ic_weather_one_cloud)
            "RAIN_DAY" -> binding.imageViewToday.setImageResource(R.drawable.ic_weather_rain)
            "SNOW_DAY" -> binding.imageViewToday.setImageResource(R.drawable.ic_weather_snow)
            "STORM_DAY" -> binding.imageViewToday.setImageResource(R.drawable.ic_weather_thunder)
            "FOG_DAY" -> binding.imageViewToday.setImageResource(R.drawable.ic_weather_fog)
            "CLEAR_NIGHT" -> binding.imageViewToday.setImageResource(R.drawable.ic_weather_clear_night)
            else -> binding.imageViewToday.setImageResource(R.drawable.ic_weather_clear_day)
        }

        when(temperature.tomorrow.icon){
            "CLEAR_DAY" -> binding.imageViewTomorrow.setImageResource(R.drawable.ic_weather_clear_day)
            "SCATTERED_CLOUDS_DAY" -> binding.imageViewTomorrow.setImageResource(R.drawable.ic_weather_some_clouds)
            "BROKEN_OVERCAST_CLOUDS_DAY" -> binding.imageViewTomorrow.setImageResource(R.drawable.ic_weather_one_cloud)
            "RAIN_DAY" -> binding.imageViewTomorrow.setImageResource(R.drawable.ic_weather_rain)
            "SNOW_DAY" -> binding.imageViewTomorrow.setImageResource(R.drawable.ic_weather_snow)
            "STORM_DAY" -> binding.imageViewTomorrow.setImageResource(R.drawable.ic_weather_thunder)
            "FOG_DAY" -> binding.imageViewTomorrow.setImageResource(R.drawable.ic_weather_fog)
            "CLEAR_NIGHT" -> binding.imageViewTomorrow.setImageResource(R.drawable.ic_weather_clear_night)
            else -> binding.imageViewTomorrow.setImageResource(R.drawable.ic_weather_clear_day)
        }

        when(temperature.dayAfterTomorrow.icon){
            "CLEAR_DAY" -> binding.imageViewAfterTomorrow.setImageResource(R.drawable.ic_weather_clear_day)
            "SCATTERED_CLOUDS_DAY" -> binding.imageViewAfterTomorrow.setImageResource(R.drawable.ic_weather_some_clouds)
            "BROKEN_OVERCAST_CLOUDS_DAY" -> binding.imageViewAfterTomorrow.setImageResource(R.drawable.ic_weather_one_cloud)
            "RAIN_DAY" -> binding.imageViewAfterTomorrow.setImageResource(R.drawable.ic_weather_rain)
            "SNOW_DAY" -> binding.imageViewAfterTomorrow.setImageResource(R.drawable.ic_weather_snow)
            "STORM_DAY" -> binding.imageViewAfterTomorrow.setImageResource(R.drawable.ic_weather_thunder)
            "FOG_DAY" -> binding.imageViewAfterTomorrow.setImageResource(R.drawable.ic_weather_fog)
            "CLEAR_NIGHT" -> binding.imageViewAfterTomorrow.setImageResource(R.drawable.ic_weather_clear_night)
            else -> binding.imageViewAfterTomorrow.setImageResource(R.drawable.ic_weather_clear_day)
        }

    }

    private fun fetchWeatherData(){
        mainViewModel.getWeatherData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}