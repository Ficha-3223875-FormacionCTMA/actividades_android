package com.example.miformacionctma.data.remote

fun interface TokenProvider {
    fun obtenerToken(): String?
}