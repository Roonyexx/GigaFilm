package com.gigaprod.gigafilm.network

import Content
import androidx.lifecycle.lifecycleScope
import com.gigaprod.gigafilm.adapter.MovieAdapter
import com.gigaprod.gigafilm.api.ApiClient
import com.gigaprod.gigafilm.api.StandartResponse
import com.gigaprod.gigafilm.api.contentStatus
import com.gigaprod.gigafilm.api.mediaApi
import kotlinx.coroutines.launch
import retrofit2.Response




class ServerRepository() {
    private val api: mediaApi = ApiClient.serverMediaApi

    suspend fun getRecommendations(): List<Content> {
        var result: Result<List<Content>>
        do {
            result = try {
                val response = api.getRecommendations()
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        } while (result.isFailure)

        return result.getOrNull()!!
    }

    suspend fun setContentStatus(request: contentStatus): StandartResponse {
        var result: Result<StandartResponse>
        do {
            result = try {
                val response: Response<StandartResponse> = api.setContentStatus(request)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Ошибка: ${response.code()} ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        } while (result.isFailure)
        return result.getOrNull()!!
    }

    suspend fun searchMovies(query: String): List<Content> {
        var result: Result<List<Content>>
        do {
            result = try {
                val response = api.searchMovies(query)
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        } while (result.isFailure)
        return result.getOrNull()!!
    }

    suspend fun getUserFilms(): List<Content> {
        var result: Result<List<Content>>
        do {
            result = try {
                val response = api.getUserFilms()
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        } while(result.isFailure)
        return result.getOrNull()!!
    }
}
