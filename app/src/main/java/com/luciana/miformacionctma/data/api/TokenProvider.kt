package com.luciana.miformacionctma.data.api

fun interface TokenProvider {
    suspend fun token(): String?
}

object EmptyTokenProvider : TokenProvider {
    override suspend fun token(): String? {
        return "token-APR-01"
    }
}