package com.luciana.miformacionctma.data.api

import okhttp3.Interceptor
import okhttp3.Response

class BearerTokenInterceptor(
    private val tokenProvider: TokenProvider
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val token = kotlinx.coroutines.runBlocking {
            tokenProvider.token()
        }

        val request = chain.request()
            .newBuilder()
            .apply {
                if (!token.isNullOrBlank()) {
                    header("Authorization", token)
                }
            }
            .build()

        return chain.proceed(request)
    }
}