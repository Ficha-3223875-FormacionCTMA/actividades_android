package com.example.miformacionctma.data

object AuthManager {

    private var token: String? = "token-APR-01"

    fun guardarToken(nuevoToken: String) {
        token = nuevoToken
    }

    fun obtenerToken(): String? {
        return token
    }

    fun limpiarToken() {
        token = null
    }
}