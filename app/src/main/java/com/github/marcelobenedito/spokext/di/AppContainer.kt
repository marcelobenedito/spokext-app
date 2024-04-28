package com.github.marcelobenedito.spokext.di

import android.content.Context
import com.github.marcelobenedito.spokext.data.SpokextVoiceToTextListener
import com.github.marcelobenedito.spokext.data.VoiceToTextListener


interface AppContainer {
    val listener: VoiceToTextListener
}

class DefaultAppContainer(context: Context) : AppContainer {
    override val listener: VoiceToTextListener by lazy {
        SpokextVoiceToTextListener(context)
    }
}