package com.woodny.weathernewsapplication.module

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.woodny.weathernewsapplication.model.api.NewsApi
import com.woodny.weathernewsapplication.model.api.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ClientApiModule {
    companion object {
        const val WEATHER_URL = "https://api.openweathermap.org"
        const val NEWS_URL = "https://bing-news-search1.p.rapidapi.com"
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Singleton
    @Provides
    fun provideWeatherApiRetrofit(moshi: Moshi): WeatherApi =
        Retrofit.Builder()
            .baseUrl(WEATHER_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(WeatherApi::class.java)

    @Singleton
    @Provides
    fun provideNewsApiRetrofit(moshi: Moshi): NewsApi =
        Retrofit.Builder()
            .baseUrl(NEWS_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(NewsApi::class.java)

}