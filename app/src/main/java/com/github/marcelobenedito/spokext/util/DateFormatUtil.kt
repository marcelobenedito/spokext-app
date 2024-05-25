package com.github.marcelobenedito.spokext.util

import android.icu.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateFormatUtil {
    companion object {
        fun formatTimestamp(timestamp: Long, pattern: String = "yyyy/MM/dd"): String {
            val date = Date(timestamp)
            val formatter = SimpleDateFormat(pattern, Locale.getDefault())
            return formatter.format(date)
        }
    }
}