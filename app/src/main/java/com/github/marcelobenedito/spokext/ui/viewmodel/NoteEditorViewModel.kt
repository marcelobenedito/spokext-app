package com.github.marcelobenedito.spokext.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.github.marcelobenedito.spokext.SpokextApplication
import com.github.marcelobenedito.spokext.data.SpokextRepository
import com.github.marcelobenedito.spokext.data.VoiceToTextListener
import com.github.marcelobenedito.spokext.data.VoiceToTextListenerState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class NoteEditorViewModel(private val repository: SpokextRepository) : ViewModel() {
    // Speaking and real time recognized speech state
    val state: StateFlow<VoiceToTextListenerState> = repository
        .getListeningState()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = VoiceToTextListenerState()
        )

    /**
     * Change the current notes by typing through keyboard instead on the mic.
     * [text] is the new value to be added.
     */
    fun onTextChange(text: String) {
        repository.onTextChange(text)
    }

    /**
     * Starts recognizing voice through the mic.
     * The isSpeaking state are changed to true.
     */
    fun startListening() {
        repository.startListening()
    }

    /**
     * Stops recognizing voice through the mic.
     * The isSpeaking state are changed to false.
     */
    fun stopListening() {
        repository.stopListening()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val appContainer = (this[APPLICATION_KEY] as SpokextApplication).appContainer
                NoteEditorViewModel(repository = appContainer.repository)
            }
        }
    }
}
