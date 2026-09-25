package com.example.miformacionctma.data.remote

import com.example.miformacionctma.data.ApiService

class RemoteActividadDataSource(
    private val api: ApiService
) {

    suspend fun obtenerActividades(
        token: String
    ): ActividadResponseDto {

        return api.obtenerActividades(
            token = token
        )
    }

    suspend fun crearActividad(
        token: String,
        actividad: CrearActividadDto
    ): ActividadDto {

        return api.crearActividad(
            token = token,
            actividad = actividad
        )
    }

    suspend fun subirEvidencia(
        actividadId: String,
        token: String,
        archivo: okhttp3.MultipartBody.Part
    ): EvidenciaResponseDto {

        return api.subirEvidencia(
            actividadId = actividadId,
            token = token,
            archivo = archivo
        )
    }
}