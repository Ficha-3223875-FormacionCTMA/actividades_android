package com.example.miformacionctma.data

fun interface TokenProvider {
    fun currentAccessToken(): String?
}