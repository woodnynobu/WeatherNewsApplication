package com.woodny.weathernewsapplication.model.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveCity(cityName: String)
    suspend fun loadCity(): Flow<String>
}