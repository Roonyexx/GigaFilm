package com.gigaprod.gigafilm.ui.custom

import com.gigaprod.gigafilm.R
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

fun getVoteColor(rating: Float): Int {
    return when {
        rating <= 4f -> R.color.rating_red
        rating <= 6f -> R.color.rating_gray
        else -> R.color.rating_green
    }
}