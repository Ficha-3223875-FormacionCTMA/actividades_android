package com.example.miformacionctma.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class EvidenciaResponseDto(
    val mensaje: String,
    val evidencia: EvidenciaDto
)

@Serializable
data class EvidenciaDto(
    val id: String,
    val actividadId: String,
    val nombreArchivo: String,
    val nombreGuardado: String,
    val tipoArchivo: String,
    val sizeBytes: Long,
    val estado: String,
    val createdAt: String
)