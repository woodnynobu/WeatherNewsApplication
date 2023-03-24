package com.woodny.weathernewsapplication.model.api

import com.woodny.weathernewsapplication.model.data.GeoCodingResponse
import com.woodny.weathernewsapplication.model.data.WeatherInfoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("geo/1.0/direct")
    fun fetchGeoCoding(
        @Query("q") cityName: String,
        @Query("appid") appKey: String,
    ): Call<Array<GeoCodingResponse>>

    @GET("data/3.0/onecall")
    fun fetchWeatherInfo(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appKey: String,
        @Query("exclude") exclude: String,
        @Query("units") units: String
    ): Call<WeatherInfoResponse>

}