package com.gigaprod.gigafilm.api

import Content
import retrofit2.http.GET
import retrofit2.http.Query

interface searchApi {
    @GET("get_info/search")
    suspend fun searchMovies(@Query("query") query: String): List <Content>
}