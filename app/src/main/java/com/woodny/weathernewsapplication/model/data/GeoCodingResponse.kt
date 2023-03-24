package com.woodny.weathernewsapplication.model.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GeoCodingResponse(
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String,
)
