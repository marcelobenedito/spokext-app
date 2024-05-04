package com.github.marcelobenedito.spokext.ui

import java.util.Calendar

data class Note(
    val id: Int = 0,
    val title: String,
    val description: String?,
    val date: Long = Calendar.getInstance().timeInMillis,
    val isDone: Boolean = false
)
