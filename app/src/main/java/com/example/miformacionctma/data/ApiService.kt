package com.example.miformacionctma.data

import com.example.miformacionctma.data.remote.ActividadDto
import com.example.miformacionctma.data.remote.ActividadesResponseDto
import com.example.miformacionctma.data.remote.CrearActividadDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("api/actividades")
    suspend fun obtenerActividades(): ActividadesResponseDto

    @POST("api/actividades")
    suspend fun crearActividad(
        @Body actividad: CrearActividadDto
    ): ActividadDto
}