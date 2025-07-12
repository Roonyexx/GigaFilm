package com.gigaprod.gigafilm.api

import Content
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

@Serializable
data class contentStatus(
    val content_id: Int,
    val content_type: String,
    val status_id: Int
)

interface mediaApi {
    @POST("recommend/new_films")
    suspend fun getRecommendations(): List<Content>
    @POST("write_info/content_status")
    suspend fun  setContentStatus(@Body request: contentStatus) : Response<StandartResponse>
    @GET("get_info/search")
    suspend fun searchMovies(@Query("query") query: String): List <Content>
    @GET("get_info/user_films")
    suspend fun getUserFilms(): List<Content>
}