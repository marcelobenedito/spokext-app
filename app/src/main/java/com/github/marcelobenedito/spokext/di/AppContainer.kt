package com.github.marcelobenedito.spokext.di

import android.content.Context
import com.github.marcelobenedito.spokext.data.SpokextRepository
import com.github.marcelobenedito.spokext.data.SpokextVoiceToTextListener
import com.github.marcelobenedito.spokext.data.VoiceToTextListener
import com.github.marcelobenedito.spokext.data.database.SpokextDatabase


interface AppContainer {
    val listener: VoiceToTextListener
    val database: SpokextDatabase
    val repository: SpokextRepository
}

class DefaultAppContainer(context: Context) : AppContainer {
    override val listener: VoiceToTextListener by lazy {
        SpokextVoiceToTextListener(context)
    }

    override val database: SpokextDatabase by lazy {
        SpokextDatabase.getDatabase(context)
    }

    override val repository: SpokextRepository by lazy {
        SpokextRepository(database.spokextDao(), listener)
    }
}