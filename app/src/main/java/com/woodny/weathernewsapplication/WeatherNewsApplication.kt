package com.woodny.weathernewsapplication

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeatherNewsApplication : Application(){

    // TODO: Timberを導入予定
    override fun onCreate() {
        super.onCreate()
    }

}