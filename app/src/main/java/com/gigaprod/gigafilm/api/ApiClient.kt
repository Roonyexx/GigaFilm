package com.gigaprod.gigafilm.api

import android.content.Context
import com.gigaprod.gigafilm.network.JsonProvider
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://10.0.2.2:8000/"
    private var retrofit: Retrofit? = null
    private var token: String? = null
    lateinit var serverAuthApi: authApi private set
    lateinit var serverSearchApi: searchApi private set
    lateinit var serverProfileApi: profileApi private set

    lateinit var serverMediaApi: mediaApi private set
    private lateinit var appContext: Context

    fun setToken(newToken: String) {
        token = newToken
        init(appContext)
    }

    fun init(context: Context) {
        appContext = context.applicationContext
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getSafeOkHttpClient(context, token))
            .addConverterFactory(JsonProvider.json.asConverterFactory(("application/json".toMediaType())))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        serverAuthApi = retrofit!!.create(authApi::class.java)
        serverSearchApi = retrofit!!.create(searchApi::class.java)
        serverProfileApi = retrofit!!.create(profileApi::class.java)
        serverMediaApi = retrofit!!.create(mediaApi::class.java)
    }
}
