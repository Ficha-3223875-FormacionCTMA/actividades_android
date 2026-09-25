package com.example.miformacionctma.data

import com.example.miformacionctma.data.remote.ActividadDto
import com.example.miformacionctma.data.remote.ActividadResponseDto
import com.example.miformacionctma.data.remote.CrearActividadDto
import com.example.miformacionctma.data.remote.EvidenciaResponseDto

import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    @GET("api/actividades")
    suspend fun obtenerActividades(
        @Header("Authorization") token: String
    ): ActividadResponseDto

    @POST("api/actividades")
    suspend fun crearActividad(
        @Header("Authorization") token: String,
        @Body actividad: CrearActividadDto
    ): ActividadDto

    @Multipart
    @POST("api/actividades/{actividadId}/evidencias")
    suspend fun subirEvidencia(
        @Path("actividadId") actividadId: String,
        @Header("Authorization") token: String,
        @Part archivo: MultipartBody.Part
    ): EvidenciaResponseDto
}