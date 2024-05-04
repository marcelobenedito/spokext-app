package com.github.marcelobenedito.spokext.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class SpokextDatabase : RoomDatabase() {
    abstract fun spokextDao(): SpokextDao

    companion object {
        @Volatile
        private var Instance: SpokextDatabase? = null

        fun getDatabase(context: Context): SpokextDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, SpokextDatabase::class.java, "spokext_databsae")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}