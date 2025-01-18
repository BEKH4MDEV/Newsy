package com.bekhamdev.newsy.core.presentation.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun formatPublishedAtDate(publishedAt: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMMM", Locale.getDefault())
    return try {
        val date = inputFormat.parse(publishedAt)
        outputFormat.format(date!!)
    } catch (e: Exception) {
        e.printStackTrace()
        "Unknown Date" // Handle parsing errors gracefully
    }
}