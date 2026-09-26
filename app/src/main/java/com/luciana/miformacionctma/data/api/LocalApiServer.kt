package com.luciana.miformacionctma.data.api

/**
 * Archivo conservado como referencia de la simulación local anterior.
 *
 * La entrega actual usa el servidor FastAPI de api/main.py.
 * RetrofitClient apunta a 10.0.2.2:8000 para el emulador.
 */
object LocalApiServer {
    const val HOST = "10.0.2.2"
    const val PORT = 8000
    const val BASE_URL = "http://$HOST:$PORT/"
    fun start() = Unit
    fun stop() = Unit
}
