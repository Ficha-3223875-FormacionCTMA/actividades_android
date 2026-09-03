package com.example.miformacionctma.model

enum class Prioridad { BAJA, MEDIA, ALTA }

data class ActividadFormativa(
    val id: Long,
    val titulo: String,
    val descripcion: String? = null,
    val progreso: Int = 0,
    val diasRestantes: Int = 0,
    val prioridad: Prioridad = Prioridad.MEDIA,
    val fechaEntrega: String = ""
)
