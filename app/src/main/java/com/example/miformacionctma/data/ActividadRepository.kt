package com.example.miformacionctma.data

class ActividadRepository {

    private val api = RetrofitClient.apiService

    suspend fun obtenerActividades(): List<Actividad> {
        return api.obtenerActividades(
            AuthManager.token
        ).actividades
    }

    suspend fun crearActividad(
        actividad: Actividad
    ): Actividad {
        return api.crearActividad(
            AuthManager.token,
            actividad
        )
    }
}