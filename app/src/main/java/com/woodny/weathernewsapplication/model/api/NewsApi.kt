package com.woodny.weathernewsapplication.model.api

import com.woodny.weathernewsapplication.model.data.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("news")
    fun fetchNewsInfo(
        @Query("category") category: String,
        @Query("rapidapi-key") apiKey: String,
    ): Call<NewsResponse>

}