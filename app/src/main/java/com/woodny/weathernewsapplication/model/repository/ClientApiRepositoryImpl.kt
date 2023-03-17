package com.woodny.weathernewsapplication.model.repository

import android.util.Log
import com.woodny.weathernewsapplication.BuildConfig
import com.woodny.weathernewsapplication.model.api.NewsApi
import com.woodny.weathernewsapplication.model.api.WeatherApi
import com.woodny.weathernewsapplication.model.data.NewsResponse
import com.woodny.weathernewsapplication.model.data.WeatherInfoResponse
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named

class ClientApiRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val newsApi: NewsApi
) : ClientApiRepository {
    companion object {
        const val WEATHER_DAILY_COUNT = 2
        const val WEATHER_UNITS = "metric"
        const val NEWS_PAGE_SIZE = 20
//        const val NEWS_COUNTRY_JAPAN = "jp"
//        const val NEWS_COUNTRY_USA = "us"
    }

    override suspend fun fetchWeatherInfo(cityName: String): WeatherInfoResponse? {
        try {
            val response = weatherApi.fetchWeatherInfo(
                cityName,
                BuildConfig.WEATHER_API_KEY,
                WEATHER_DAILY_COUNT,
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

    override suspend fun fetchNewsInfo(category: String, country: String): NewsResponse? {
        try {
            val response = newsApi.fetchNewsInfo(
                category,
                country,
                BuildConfig.NEWS_API_KEY,
                NEWS_PAGE_SIZE
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