package com.example.miformacionctma.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class CrearActividadDto(
    val titulo: String,
    val descripcion: String,
    val aprendiz: String = "APR-01"
)