package com.example.miformacionctma.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class ActividadResponseDto(
    val total: Int,
    val actividades: List<ActividadDto>
)