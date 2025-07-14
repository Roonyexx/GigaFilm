package com.gigaprod.gigafilm.api

import Content
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

@Serializable
data class ContentBase(
    val id: Int,
    val content_type: String
)

@Serializable
data class ContentStatus(
    val content_id: Int,
    val content_type: String,
    val status_id: Int
)

@Serializable
data class ContentScore(
    val content_id: Int,
    val content_type: String,
    val user_score: Int
)


@Serializable
data class ContentSource(
    val link: String,
    val img_src: String
)

interface mediaApi {
    @POST("recommend/new_films")
    suspend fun getRecommendations(): List<Content>
    @POST("write_info/content_status")
    suspend fun  setContentStatus(@Body request: ContentStatus) : Response<StandartResponse>
    @POST("write_info/content_user_score")
    suspend fun setUserScore(@Body request: ContentScore) : Response<StandartResponse>
    @GET("get_info/search")
    suspend fun searchMovies(@Query("query") query: String): List <Content>
    @GET("get_info/user_films")
    suspend fun getUserFilms(): List<Content>
    @POST("get_info/where_to_watch")
    suspend fun whereToWatch(@Body request: ContentBase) : List<ContentSource>

}