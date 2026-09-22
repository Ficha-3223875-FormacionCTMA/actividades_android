package com.example.miformacionctma.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class ActividadesResponseDto(
    val total: Int = 0,
    val actividades: List<ActividadDto> = emptyList()
)