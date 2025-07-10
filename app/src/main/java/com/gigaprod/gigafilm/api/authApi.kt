package com.gigaprod.gigafilm.api

import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
@Serializable
data class LoginRequest(val username: String, val password: String)
@Serializable
data class LoginResponse(val ok: Boolean, val access_token: String)
@Serializable
data class RegisterRequest(val username: String, val password: String)
@Serializable
data class RegisterResponse(val ok: Boolean)

interface authApi {
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}


