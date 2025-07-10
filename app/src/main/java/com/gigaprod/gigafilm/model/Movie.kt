package com.gigaprod.gigafilm.model

data class Movie(
    val title: String,
    val description: String,
    val poster_path: String,
    var userRating: Float? = null
)