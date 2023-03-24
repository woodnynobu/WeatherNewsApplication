package com.woodny.weathernewsapplication.model.repository

import com.woodny.weathernewsapplication.model.data.GeoCodingResponse
import com.woodny.weathernewsapplication.model.data.NewsResponse
import com.woodny.weathernewsapplication.model.data.WeatherInfoResponse

interface ClientApiRepository {
    suspend fun fetchGeoCodingInfo(cityName: String): Array<GeoCodingResponse>?
    suspend fun fetchWeatherInfo(lat: Double, lon: Double): WeatherInfoResponse?
    suspend fun fetchNewsInfo(category: String): NewsResponse?
}