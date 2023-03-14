package com.woodny.weathernewsapplication.module

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.woodny.weathernewsapplication.model.api.ClientApi
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
        const val NEWS_URL = "https://newsapi.org"
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Singleton
    @Provides
    fun provideClientApi(retrofit: Retrofit): ClientApi =
        retrofit.create(ClientApi::class.java)

    @Singleton
    @Provides
    fun provideRetrofit(moshi: Moshi): Retrofit =
        Retrofit.Builder()
            .baseUrl(WEATHER_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

}