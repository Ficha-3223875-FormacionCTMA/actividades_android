package com.luciana.miformacionctma.data.api

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ActividadApiService {

    @GET("api/actividades")
    suspend fun obtenerActividades(
        @Header("Authorization") token: String
    ): Response<ActividadResponseDto>

    @GET("api/actividades/{id}")
    suspend fun obtenerActividad(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Response<ActividadDto>

    @POST("api/actividades")
    suspend fun crearActividad(
        @Header("Authorization") token: String,
        @Body actividad: ActividadCreateDto
    ): Response<ActividadDto>

    @PUT("api/actividades/{id}")
    suspend fun actualizarActividad(
        @Path("id") id: String,
        @Header("Authorization") token: String,
        @Body actividad: ActividadUpdateDto
    ): Response<ActividadDto>

    @DELETE("api/actividades/{id}")
    suspend fun eliminarActividad(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Response<EliminarActividadDto>

    @PATCH("api/actividades/{id}/estado")
    suspend fun cambiarEstado(
        @Path("id") id: String,
        @Header("Authorization") token: String,
        @Body estado: EstadoUpdateDto
    ): Response<ActividadDto>

    @Multipart
    @POST("api/actividades/{id}/evidencias")
    suspend fun subirEvidencia(
        @Path("id") id: String,
        @Header("Authorization") token: String,
        @Part archivo: MultipartBody.Part
    ): Response<EvidenciaResponseDto>

    @GET("api/actividades/{id}/evidencias")
    suspend fun listarEvidencias(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Response<EvidenciasResponseDto>
}