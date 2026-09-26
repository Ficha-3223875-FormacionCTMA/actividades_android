package com.luciana.miformacionctma.data.api

import kotlinx.serialization.json.Json
import com.luciana.miformacionctma.BuildConfig
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private val BASE_URL = BuildConfig.API_BASE_URL
    private val json = Json { ignoreUnknownKeys = true; isLenient = true; explicitNulls = false }
    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .callTimeout(30, TimeUnit.SECONDS)
        .proxy(java.net.Proxy.NO_PROXY)
        .build()

    val actividadApi: ActividadApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(ActividadApiService::class.java)
    }
}
