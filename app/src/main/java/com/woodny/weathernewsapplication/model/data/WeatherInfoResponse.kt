package com.woodny.weathernewsapplication.model.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherInfoResponse(
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset : Int,
    val daily: List<WeatherList>,
)

data class WeatherList(
    val dt: Int,
    val sunrise: Int,
    val sunset: Int,
    val moonrise: Int,
    val moonset: Int,
    @Json(name = "moon_phase")
    val moonPhase: Double,
    val temp: Temp,
    @Json(name = "feels_like")
    val feelsLike: FeelsLike,
    val pressure: String,
    val humidity: Int,
    @Json(name = "dew_point")
    val dewPoint: Double,
    @Json(name = "wind_speed")
    val windSpeed: Double,
    @Json(name = "wind_deg")
    val windDeg: Int,
    @Json(name = "wind_gust")
    val windGust: Double,
    val weather: List<Weather>,
    val clouds: Int,
    val pop: Double,
    val uvi: Double,
)

data class FeelsLike(
    val day: Double,
    val night: Double,
    val eve: Double,
    val morn: Double,
)

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Temp(
    val day: Double,
    val min: Double,
    val max: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)
