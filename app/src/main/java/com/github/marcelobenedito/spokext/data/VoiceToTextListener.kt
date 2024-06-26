package com.github.marcelobenedito.spokext.data

import android.speech.RecognitionListener
import kotlinx.coroutines.flow.Flow

interface VoiceToTextListener : RecognitionListener {
    fun startListening(languageCode: String = "en")
    fun stopListening()
    fun onTextChange(value: String)
    fun getListeningState(): Flow<VoiceToTextListenerState>
    fun cleanState()
}
