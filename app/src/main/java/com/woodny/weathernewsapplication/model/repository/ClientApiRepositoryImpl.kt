package com.woodny.weathernewsapplication.model.repository

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.woodny.weathernewsapplication.BuildConfig
import com.woodny.weathernewsapplication.model.api.ClientApi
import com.woodny.weathernewsapplication.model.data.WeatherInfoResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
import javax.inject.Inject

class ClientApiRepositoryImpl @Inject constructor(
    private val clientApi: ClientApi
) : ClientApiRepository {
    companion object {

        const val WEATHER_DAILY_COUNT = 2
        const val WEATHER_UNITS = "metric"
    }

    override suspend fun fetchWeatherInfo(cityName: String): WeatherInfoResponse? {
        try {
            val response = clientApi.fetchWeatherInfo(
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
}