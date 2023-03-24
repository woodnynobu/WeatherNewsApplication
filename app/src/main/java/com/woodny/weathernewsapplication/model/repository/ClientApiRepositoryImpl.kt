package com.woodny.weathernewsapplication.model.repository

import android.util.Log
import com.woodny.weathernewsapplication.BuildConfig
import com.woodny.weathernewsapplication.model.api.NewsApi
import com.woodny.weathernewsapplication.model.api.WeatherApi
import com.woodny.weathernewsapplication.model.data.GeoCodingResponse
import com.woodny.weathernewsapplication.model.data.NewsResponse
import com.woodny.weathernewsapplication.model.data.WeatherInfoResponse
import java.io.IOException
import javax.inject.Inject

class ClientApiRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val newsApi: NewsApi
) : ClientApiRepository {
    companion object {
        const val WEATHER_EXCLUDE = "current,minutely,hourly,alerts"
        const val WEATHER_UNITS = "metric"
//        const val NEWS_PAGE_SIZE = 20
//        const val NEWS_COUNTRY_JAPAN = "jp"
//        const val NEWS_COUNTRY_USA = "us"
    }

    override suspend fun fetchGeoCodingInfo(cityName: String): Array<GeoCodingResponse>? {
        try {
            val response = weatherApi.fetchGeoCoding(
                cityName,
                BuildConfig.WEATHER_API_KEY,
            ).execute()
            if (response.isSuccessful) {
                return response.body()
            } else {
                // TODO:失敗の時の処理は未実装
                Log.d("ClientApiRepository", "GET ERROR")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    override suspend fun fetchWeatherInfo(lat: Double, lon: Double): WeatherInfoResponse? {
        try {
            val response = weatherApi.fetchWeatherInfo(
                lat,
                lon,
                BuildConfig.WEATHER_API_KEY,
                WEATHER_EXCLUDE,
                WEATHER_UNITS
            ).execute()
            if (response.isSuccessful) {
                return response.body()
            } else {
                // TODO:失敗の時の処理は未実装
                Log.d("ClientApiRepository", "GET ERROR")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    override suspend fun fetchNewsInfo(category: String): NewsResponse? {
        try {
            val response = newsApi.fetchNewsInfo(
                category,
                BuildConfig.NEWS_API_KEY,
            ).execute()
            if (response.isSuccessful) {
                return response.body()
            } else {
                // TODO:失敗の時の処理は未実装
                Log.d("ClientApiRepository", "GET ERROR")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
}