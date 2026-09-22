package com.example.miformacionctma.data.remote

import com.example.miformacionctma.data.ApiService

class RemoteActividadDataSource(
    private val api: ApiService
) {

    suspend fun obtenerActividades(): List<ActividadDto> {
        val respuesta = classifyNetworkError {
            api.obtenerActividades()
        }

        return respuesta.getOrElse { error ->
            throw error
        }.actividades
    }

    suspend fun crearActividad(
        actividad: CrearActividadDto
    ): ActividadDto {

        val respuesta = classifyNetworkError {
            api.crearActividad(actividad)
        }

        return respuesta.getOrElse { error ->
            throw error
        }
    }
}