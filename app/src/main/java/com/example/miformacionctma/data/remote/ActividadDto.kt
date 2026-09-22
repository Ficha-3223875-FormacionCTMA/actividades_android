package com.example.miformacionctma.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActividadDto(
    val id: String = "",
    val titulo: String,
    val descripcion: String,
    val aprendiz: String = "APR-01",
    val estado: String = "PENDIENTE",
    @SerialName("created_at")
    val createdAt: String = ""
)