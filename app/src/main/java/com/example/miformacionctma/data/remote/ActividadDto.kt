package com.example.miformacionctma.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class ActividadDto(
    val id: String,
    val titulo: String,
    val descripcion: String,
    val aprendiz: String,
    val estado: String,
    val createdAt: String
)