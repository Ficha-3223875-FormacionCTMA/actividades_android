package com.luciana.miformacionctma.domain


enum class Prioridad {
    BAJA,
    MEDIA,
    ALTA
}

enum class EstadoActividad {
    PENDIENTE,
    EN_PROCESO,
    COMPLETADA,
    VENCIDA
}

data class ActividadFormativa(
    val id: Long,
    val titulo: String,
    val descripcion: String? = null,
    val progreso: Int,
    val diasRestantes: Int,
    val prioridad: Prioridad,
    val fecha: String = ""
)
