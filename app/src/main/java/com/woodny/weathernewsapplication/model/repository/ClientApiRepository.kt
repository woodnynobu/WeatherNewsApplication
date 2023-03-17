package com.woodny.weathernewsapplication.model.repository

import com.woodny.weathernewsapplication.model.data.NewsResponse
import com.woodny.weathernewsapplication.model.data.WeatherInfoResponse

interface ClientApiRepository {
    suspend fun fetchWeatherInfo(cityName: String): WeatherInfoResponse?
    suspend fun fetchNewsInfo(category: String, country: String): NewsResponse?
}