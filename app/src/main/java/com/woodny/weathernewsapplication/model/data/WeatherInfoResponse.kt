package com.woodny.weathernewsapplication.model.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherInfoResponse(
    val city: City,
    val cod: String,
    val message: Double,
    val cnt : Int,
    val list: List<WeatherList>,
)

data class City(
    val id: Int,
    val name: String,
    val coord: Coord,
    val country: String,
    val population: Int,
    val timezone: Int,
)

data class Coord(
    val lon: Double,
    val lat: Double
)

data class WeatherList(
    val dt: Int,
    val sunrise: Int,
    val sunset: Int,
    val temp: Temp,
    @Json(name = "feels_like")
    val feelsLike: FeelsLike,
    val pressure: String,
    val humidity: Int,
    val weather: List<Weather>,
    val speed: Double,
    val deg: Int,
    val gust: Double,
    val clouds: Int,
    val pop: Double,
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
