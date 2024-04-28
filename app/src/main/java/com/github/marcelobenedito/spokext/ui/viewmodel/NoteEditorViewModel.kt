package com.github.marcelobenedito.spokext.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.github.marcelobenedito.spokext.SpokextApplication
import com.github.marcelobenedito.spokext.data.VoiceToTextListener
import com.github.marcelobenedito.spokext.data.VoiceToTextListenerState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class NoteEditorViewModel(private val listener: VoiceToTextListener) : ViewModel() {

    val state: StateFlow<VoiceToTextListenerState> = listener
        .getListeningState()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = VoiceToTextListenerState()
        )

    fun onTextChange(text: String) {
        listener.onTextChange(text)
    }

    fun startListening() {
        listener.startListening()
    }

    fun stopListening() {
        listener.stopListening()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val appContainer = (this[APPLICATION_KEY] as SpokextApplication).appContainer
                NoteEditorViewModel(listener = appContainer.listener)
            }
        }
    }
}
