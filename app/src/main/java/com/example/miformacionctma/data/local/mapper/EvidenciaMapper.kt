package com.example.miformacionctma.data.local.mapper

import com.example.miformacionctma.data.local.entity.EvidenciaEntity
import com.example.miformacionctma.domain.Evidencia

fun EvidenciaEntity.toEvidenciaDomain(): Evidencia {
    return Evidencia(
        id = id,
        actividadId = actividadId,
        localUri = localUri,
        mimeType = mimeType,
        sizeBytes = sizeBytes,
        estado = estado,
        creadaEnEpochMillis = creadaEnEpochMillis
    )
}

fun Evidencia.toEvidenciaEntity(): EvidenciaEntity {
    return EvidenciaEntity(
        id = id,
        actividadId = actividadId,
        localUri = localUri,
        mimeType = mimeType,
        sizeBytes = sizeBytes,
        estado = estado,
        creadaEnEpochMillis = creadaEnEpochMillis
    )
}