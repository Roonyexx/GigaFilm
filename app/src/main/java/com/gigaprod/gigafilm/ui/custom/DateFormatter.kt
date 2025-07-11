package com.gigaprod.gigafilm.ui.custom

import java.text.SimpleDateFormat
import java.util.Locale

fun formatDate(input: String?): String {
    if (input.isNullOrEmpty()) return "—"

    return try {
        val parser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val date = parser.parse(input)
        formatter.format(date!!)
    } catch (e: Exception) {
        "-"
    }
}