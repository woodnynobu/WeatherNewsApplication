package com.woodny.weathernewsapplication.model.api

import com.woodny.weathernewsapplication.model.data.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/top-headlines")
    fun fetchNewsInfo(
        @Query("category") category: String,
        @Query("country") country: String,
        @Query("apiKey") apiKey: String,
        @Query("pageSize") pageSize: Number
    ): Call<NewsResponse>

}