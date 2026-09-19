package com.example.miformacionctma.data

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

data class ActividadesResponse(
    val total: Int,
    val actividades: List<Actividad>
)

interface ApiService {

    @GET("api/actividades")
    suspend fun obtenerActividades(
        @Header("Authorization") token: String
    ): ActividadesResponse

    @POST("api/actividades")
    suspend fun crearActividad(
        @Header("Authorization") token: String,
        @Body actividad: Actividad
    ): Actividad
}