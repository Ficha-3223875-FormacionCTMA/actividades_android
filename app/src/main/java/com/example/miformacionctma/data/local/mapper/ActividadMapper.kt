package com.example.miformacionctma.data.local.mapper

import com.example.miformacionctma.data.Actividad
import com.example.miformacionctma.data.local.entity.ActividadEntity

fun ActividadEntity.toActividad(): Actividad {
    return Actividad(
        id = "ACT-${id.toString().padStart(3, '0')}",
        titulo = titulo,
        descripcion = descripcion,
        aprendiz = aprendiz,
        estado = estado,
        createdAt = createdAt
    )
}

fun Actividad.toEntity(): ActividadEntity {

    val idNumerico = id
        .removePrefix("ACT-")
        .toLongOrNull()
        ?: 0L

    return ActividadEntity(
        id = idNumerico,
        titulo = titulo,
        descripcion = descripcion,
        aprendiz = aprendiz,
        estado = estado,
        createdAt = createdAt
    )
}