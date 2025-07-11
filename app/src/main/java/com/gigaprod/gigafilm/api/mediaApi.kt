package com.gigaprod.gigafilm.api

import Content
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

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


}