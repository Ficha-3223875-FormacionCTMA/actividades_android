package com.example.miformacionctma.data.remote

import com.example.miformacionctma.data.Actividad

fun ActividadDto.toDomain(): Actividad {
    return Actividad(
        id = id,
        titulo = titulo,
        descripcion = descripcion,
        aprendiz = aprendiz,
        estado = estado,
        createdAt = createdAt
    )
}

fun Actividad.toDto(): ActividadDto {
    return ActividadDto(
        id = id,
        titulo = titulo,
        descripcion = descripcion,
        aprendiz = aprendiz,
        estado = estado,
        createdAt = createdAt
    )
}