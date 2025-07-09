package com.gigaprod.gigafilm.api

import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiClient {
    private const val BASE_URL = "https://10.0.2.2:8000/"
    private var retrofit: Retrofit? = null
    private var token: String? = null
    lateinit var serverAuthApi: authApi private set

    lateinit var serverSearchApi: searchApi private set

    fun init(context: Context) {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getSafeOkHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        serverAuthApi = retrofit!!.create(authApi::class.java)
        serverSearchApi = retrofit!!.create(searchApi::class.java)
    }
}
