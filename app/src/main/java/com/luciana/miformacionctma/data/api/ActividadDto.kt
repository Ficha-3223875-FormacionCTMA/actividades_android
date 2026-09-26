package com.luciana.miformacionctma.data.api

import com.luciana.miformacionctma.domain.ActividadFormativa
import com.luciana.miformacionctma.domain.Prioridad
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActividadResponseDto(
    val total: Int,
    val actividades: List<ActividadDto>
)

@Serializable
data class ActividadDto(
    val id: String,
    val titulo: String,
    val descripcion: String,
    val aprendiz: String,
    val estado: String,
    @SerialName("createdAt") val createdAt: String
)

@Serializable
data class ActividadCreateDto(
    val titulo: String,
    val descripcion: String,
    val aprendiz: String = "APR-01"
)

@Serializable
data class ActividadUpdateDto(
    val titulo: String,
    val descripcion: String,
    val aprendiz: String = "APR-01",
    val estado: String
)

@Serializable
data class EstadoUpdateDto(
    val estado: String
)

@Serializable
data class EliminarActividadDto(
    val mensaje: String,
    val actividadId: String
)

@Serializable
data class EvidenciaDto(
    val id: String,
    val actividadId: String,
    val nombreArchivo: String,
    val nombreGuardado: String,
    val tipoArchivo: String,
    val sizeBytes: Int,
    val estado: String,
    val createdAt: String
)

@Serializable
data class EvidenciaResponseDto(
    val mensaje: String,
    val evidencia: EvidenciaDto
)

@Serializable
data class EvidenciasResponseDto(
    val total: Int,
    val evidencias: List<EvidenciaDto>
)

fun ActividadDto.toDomain(): ActividadFormativa {
    val numericId = id.substringAfterLast("-").toLongOrNull()
        ?: (id.hashCode().toLong().let { if (it == Long.MIN_VALUE) 1L else kotlin.math.abs(it) })

    val prioridad = when (estado.uppercase()) {
        "COMPLETADA" -> Prioridad.ALTA
        "EN_PROCESO" -> Prioridad.MEDIA
        else -> Prioridad.BAJA
    }

    return ActividadFormativa(
        id = numericId,
        titulo = titulo,
        descripcion = descripcion,
        progreso = if (estado == "COMPLETADA") 100 else 0,
        diasRestantes = 0,
        prioridad = prioridad,
        fecha = createdAt.take(10),
        resuelto = estado == "COMPLETADA"
    )
}
