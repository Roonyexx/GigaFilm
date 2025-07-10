package com.gigaprod.gigafilm.api

import Content
import Movie
import retrofit2.http.GET

interface profileApi {
    @GET("get_info/user_films")
    suspend fun getUserFilms(): List<Content>
}