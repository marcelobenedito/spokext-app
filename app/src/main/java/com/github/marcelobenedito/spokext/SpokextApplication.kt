package com.github.marcelobenedito.spokext

import android.app.Application
import com.github.marcelobenedito.spokext.di.AppContainer
import com.github.marcelobenedito.spokext.di.DefaultAppContainer

class SpokextApplication : Application() {

    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = DefaultAppContainer(this)
    }
}