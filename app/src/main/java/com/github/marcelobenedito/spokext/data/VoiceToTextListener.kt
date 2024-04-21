package com.github.marcelobenedito.spokext.data

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class VoiceToTextListener(
    private val app: Application
) : RecognitionListener {

    private val _state = MutableStateFlow(VoiceToTextListenerState())
    val state = _state.asStateFlow()

    private val recognizer: SpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(app)
    private var isStopped: Boolean = false

    fun startListening(languageCode: String = "en") {
        Log.d(TAG, "startListening")
        isStopped = false
        if (!SpeechRecognizer.isRecognitionAvailable(app)) {
            Log.d(TAG, "Recognition is not available.")
            _state.update {
                it.copy(
                    error = "Recognition is not available."
                )
            }
        }

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, languageCode)
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        }

        recognizer.setRecognitionListener(this)
        recognizer.startListening(intent)

        _state.update {
            it.copy(isSpeaking = true)
        }
    }

    fun stopListening() {
        _state.update {
            it.copy(isSpeaking = false)
        }

        isStopped = true
        recognizer.stopListening()
    }

    fun onTextChange(value: String) {
        _state.update {
            it.copy(spokenText = value)
        }
    }

    override fun onReadyForSpeech(p0: Bundle?) {
        _state.update {
            it.copy(error = null)
        }
    }

    override fun onBeginningOfSpeech() = Unit

    override fun onRmsChanged(p0: Float) = Unit

    override fun onBufferReceived(p0: ByteArray?) = Unit

    override fun onEndOfSpeech() {
        Log.d(TAG, "onEndOfSpeech")
    }

    /**
     * Error constant	Constant value	What when wrong
     * ERROR_NETWORK_TIMEOUT	1	Network error.
     * ERROR_NETWORK	2	Network error.
     * ERROR_CLIENT	5	Very generic error.
     * ERROR_NO_MATCH	7	Returned if no speech was recognized during listening.
     * ERROR_RECOGNIZER_BUSY	8	Make sure you are not calling startListening before cancelling or waiting for a response.
     * ERROR_INSUFFICIENT_PERMISSIONS	9	Insufficient permissions. Make sure you requested microphone runtime permission.
     * ERROR_LANGUAGE_NOT_SUPPORTED	12	Can occur on API 31 and newer. The requested language is not supported.
     * ERROR_LANGUAGE_UNAVAILABLE	13	Can occur on API 31 and newer. The requested language is supported but not available, it's not downloaded.
     */
    override fun onError(error: Int) {
        Log.e(TAG, "Error: $error")
        if (error == SpeechRecognizer.ERROR_NO_MATCH) {
            if (!isStopped) {
                recognizer.cancel()
                startListening()
            }
        }
        if (error == SpeechRecognizer.ERROR_CLIENT) {
            return
        }

        _state.update {
            it.copy(error = "Error: $error")
        }
    }

    override fun onResults(results: Bundle?) {
        results
            ?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            ?.getOrNull(0)
            ?.let { result ->
                _state.update {
                    it.copy(spokenText = result)
                }
            }
        if (!isStopped) {
            recognizer.cancel()
            startListening()
        }
    }

    override fun onPartialResults(partialResults: Bundle?) {
        val partialMatches =
            partialResults!!.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        if (partialMatches != null && partialMatches.size > 0) {
            // Display the partial recognition result
            val partialResult = partialMatches[0]
            // TODO: need to get a way to keep old result
            _state.update {
                it.copy(spokenText = partialResult)
            }
        }
    }

    override fun onEvent(p0: Int, p1: Bundle?) = Unit

    companion object {
        private const val TAG: String = "VoiceToTextListener"
    }
}

data class VoiceToTextListenerState(
    val spokenText: String = "",
    val isSpeaking: Boolean = false,
    val error: String? = null
)