package com.example.item_realm_hilt

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class myApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}