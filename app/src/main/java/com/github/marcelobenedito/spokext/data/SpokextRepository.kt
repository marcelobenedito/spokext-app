package com.github.marcelobenedito.spokext.data

import com.github.marcelobenedito.spokext.data.database.NoteEntity
import com.github.marcelobenedito.spokext.data.database.SpokextDao
import kotlinx.coroutines.flow.Flow

class SpokextRepository(
    private val database: SpokextDao,
    private val listener: VoiceToTextListener
) {

    fun getAll(): List<NoteEntity> = database.getAll()

    fun getNoteById(id: Int): NoteEntity? = database.getNoteById(id)

    fun insert(note: NoteEntity) = database.insert(note)

    fun delete(note: NoteEntity) = database.delete(note)

    fun startListening(languageCode: String = "en") = listener.startListening(languageCode)

    fun stopListening() = listener.stopListening()

    fun onTextChange(value: String) = listener.onTextChange(value)

    fun getListeningState(): Flow<VoiceToTextListenerState> = listener.getListeningState()
}