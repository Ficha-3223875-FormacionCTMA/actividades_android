package com.example.miformacionctma.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class BearerTokenInterceptor(
    private val tokenProvider: TokenProvider
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val token = tokenProvider.obtenerToken()

        val request = chain.request()
            .newBuilder()
            .apply {
                if (!token.isNullOrBlank()) {
                    addHeader(
                        "Authorization",
                        token
                    )
                }
            }
            .build()

        return chain.proceed(request)
    }
}