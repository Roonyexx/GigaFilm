package com.gigaprod.gigafilm.api

import com.gigaprod.gigafilm.model.Movie
import retrofit2.http.POST

interface profileApi {
    @POST("get_info/user_films")
    suspend fun getUserFilms(): List<Movie>
}