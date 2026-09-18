package com.example.miformacionctma.data

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @GET("api/actividades")
    suspend fun obtenerActividades(
        @Header("Authorization") token: String
    ): List<Actividad>

    @POST("api/actividades")
    suspend fun crearActividad(
        @Header("Authorization") token: String,
        @Body actividad: Actividad
    ): Actividad
}