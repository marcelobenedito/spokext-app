package com.github.marcelobenedito.spokext.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SpokextDao {

    @Query("SELECT * FROM NoteEntity")
    fun getAll(): List<NoteEntity>

    @Query("SELECT * FROM NoteEntity WHERE id = :id")
    fun getNoteById(id: Int): NoteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: NoteEntity)

    @Delete
    fun delete(note: NoteEntity)
}