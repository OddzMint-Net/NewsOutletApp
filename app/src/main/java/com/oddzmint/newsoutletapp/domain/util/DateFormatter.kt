package com.oddzmint.newsoutletapp.domain.util

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun formatPubDate(rawDate: String?): String {
    if (rawDate == null) return ""
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        val date = inputFormat.parse(rawDate) ?: return ""
        val diffMills = System.currentTimeMillis() - date.time
        val diffHours = TimeUnit.MILLISECONDS.toHours(diffMills)

        when {
            diffHours < 1 -> "${TimeUnit.MILLISECONDS.toMinutes(diffMills)}m ago"
            diffHours < 24 -> "${diffHours}h ago"
            else -> SimpleDateFormat("MMM d", Locale.US).format(date)
        }
    } catch (e: Exception) {
        ""
    }
}