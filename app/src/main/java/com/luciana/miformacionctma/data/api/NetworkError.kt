package com.luciana.miformacionctma.data.api

sealed interface NetworkError {
    data object NoConnection : NetworkError
    data object Timeout : NetworkError
    data object Unauthorized : NetworkError
    data object NotFound : NetworkError
    data class Server(val code: Int) : NetworkError
    data object InvalidPayload : NetworkError
    data class Unknown(val cause: Throwable? = null) : NetworkError
}

class NetworkException(val error: NetworkError) : Exception(error.toString())
