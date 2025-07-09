package com.gigaprod.gigafilm.api

import com.gigaprod.gigafilm.model.Movie
import retrofit2.http.GET
import retrofit2.http.Query

interface searchApi {
    @GET("get_info/search")
    suspend fun searchMovies(@Query("query") query: String): List<Movie>
}