package com.woodny.weathernewsapplication

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class WeatherNewsApplication : Application(){

    override fun onCreate() {
        super.onCreate()

        // デバッグ時のみログ出力を許可
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}