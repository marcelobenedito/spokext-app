package com.github.marcelobenedito.spokext.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.github.marcelobenedito.spokext.SpokextApplication
import com.github.marcelobenedito.spokext.data.SpokextRepository
import com.github.marcelobenedito.spokext.data.VoiceToTextListenerState
import com.github.marcelobenedito.spokext.mapper.toEntity
import com.github.marcelobenedito.spokext.mapper.toNote
import com.github.marcelobenedito.spokext.ui.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SpokextViewModel(private val repository: SpokextRepository) : ViewModel() {
    // Speaking and real time recognized speech state
    val state: StateFlow<VoiceToTextListenerState> = repository
        .getListeningState()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = VoiceToTextListenerState()
        )

    // All stored notes list
    private val _noteList = MutableStateFlow(emptyList<Note>())
    val noteList: StateFlow<List<Note>> = _noteList.asStateFlow()

    // Indicates if is loading notes
    private val _isLoadingNotes = MutableStateFlow(false)
    val isLoadingNotes: StateFlow<Boolean> = _isLoadingNotes.asStateFlow()

    private val _isDisplayingTitleDialog = MutableStateFlow(false)
    val isDisplayingTitleDialog: StateFlow<Boolean> = _isDisplayingTitleDialog.asStateFlow()

    /**
     * Change the current notes by typing through keyboard instead on the mic.
     * [text] is the new value to be added.
     */
    fun onTextChange(text: String) {
        viewModelScope.launch {
            repository.onTextChange(text)
        }
    }

    /**
     * Starts recognizing voice through the mic.
     * The isSpeaking state are changed to true.
     */
    fun startListening() {
        viewModelScope.launch {
            repository.startListening()
        }
    }

    /**
     * Stops recognizing voice through the mic.
     * The isSpeaking state are changed to false.
     */
    fun stopListening() {
        viewModelScope.launch {
            repository.stopListening()
        }
    }

    /**
     * Save a new note to the database with the provided title and text.
     * [noteTitle] title that will be used to identify the note.
     */
    fun saveNote(noteTitle: String) {
        viewModelScope.launch {
            val newNote = Note(title = noteTitle, description = state.value.spokenText)
            withContext(Dispatchers.IO) {
                repository.insert(newNote.toEntity())
            }
            closeTitleInputDialog()
        }
    }

    /**
     * Discard the current note in the editor. It notifies success or error to the user and back to
     * the home screen.
     */
    fun discardNote() {
        viewModelScope.launch {
            repository.discardNote()
            // TODO: Send success/error notification event to the UI
        }
        // TODO: Go back to home screen. add navigation here
    }

    /**
     * Opens the title input dialog to the user provide a note title.
     */
    fun openTitleInputDialog() {
        _isDisplayingTitleDialog.update { true }
    }

    /**
     * Closes the title input dialog to the user provide a note title.
     */
    fun closeTitleInputDialog() {
        discardNote()
        _isDisplayingTitleDialog.update { false }
    }

    /**
     * Get all stored notes.
     */
    fun getAllNotes() {
        viewModelScope.launch {
            _isLoadingNotes.update { true }
            withContext(Dispatchers.IO) {
                _noteList.update { repository.getAll().map { it.toNote() }.toList() }
            }
            _isLoadingNotes.update { false }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val appContainer = (this[APPLICATION_KEY] as SpokextApplication).appContainer
                SpokextViewModel(repository = appContainer.repository)
            }
        }
    }
}
