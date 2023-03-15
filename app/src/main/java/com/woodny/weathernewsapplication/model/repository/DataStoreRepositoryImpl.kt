package com.woodny.weathernewsapplication.model.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    "settings"
)
val CITY_KEY = stringPreferencesKey("city")

class DataStoreRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
): DataStoreRepository {
    override suspend fun saveCity(cityName: String) {
        context.dataStore.edit { preferences ->
            preferences[CITY_KEY] = cityName
        }
    }

    override suspend fun loadCity(): Flow<String> =
        context.dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            // 未設定の場合、初期値を設定
            preferences[CITY_KEY] ?: "東京都"
        }
}