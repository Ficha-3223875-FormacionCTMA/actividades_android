package com.example.miformacionctma.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CrearActividadDto(
    @SerialName("titulo")
    val titulo: String,

    @SerialName("descripcion")
    val descripcion: String,

    @SerialName("aprendiz")
    val aprendiz: String
)