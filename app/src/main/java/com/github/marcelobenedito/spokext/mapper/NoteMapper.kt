package com.github.marcelobenedito.spokext.mapper

import com.github.marcelobenedito.spokext.data.database.NoteEntity
import com.github.marcelobenedito.spokext.ui.Note

/**
 * Convert a [Note] object to a [NoteEntity] entity object
 */
fun Note.toEntity(): NoteEntity = NoteEntity(
    id = id,
    title = title,
    description = description,
    date = date,
    isDone = isDone
)

/**
 * Convert an [NoteEntity] entity object to a [Note] object
 */
fun NoteEntity.toNote(): Note = Note(
    id = id,
    title = title,
    description = description,
    date = date,
    isDone = isDone
)
