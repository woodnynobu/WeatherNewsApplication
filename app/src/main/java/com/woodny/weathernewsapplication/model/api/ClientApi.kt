package com.woodny.weathernewsapplication.model.api

import com.woodny.weathernewsapplication.model.data.WeatherInfoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ClientApi {
    @GET("data/2.5/forecast/daily")
    fun fetchWeatherInfo(
        @Query("q") cityName: String,
        @Query("appid") appKey: String,
        @Query("cnt") count: Number,
        @Query("units") units: String
    ): Call<WeatherInfoResponse>

}