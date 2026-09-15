package com.example.miformacionctma.domain

enum class EstadoActividad {
    PENDIENTE,
    EN_PROCESO,
    COMPLETADA,
    VENCIDA
}

fun validarActividad(
    titulo: String,
    progreso: Int
): List<String> {

    val errores = mutableListOf<String>()

    if (titulo.trim().isEmpty()) {
        errores.add("El título es obligatorio")
    }

    if (progreso !in 0..100) {
        errores.add("El progreso debe estar entre 0 y 100")
    }

    return errores
}

fun estadoActividad(
    progreso: Int,
    diasRestantes: Int
): EstadoActividad {

    return when {
        progreso == 100 -> EstadoActividad.COMPLETADA
        diasRestantes < 0 -> EstadoActividad.VENCIDA
        progreso == 0 -> EstadoActividad.PENDIENTE
        else -> EstadoActividad.EN_PROCESO
    }
}

fun actividadesUrgentes(
    actividades: List<ActividadFormativa>
): List<ActividadFormativa> {

    return actividades.filter {
        it.progreso < 100 && it.diasRestantes <= 2
    }
}

fun promedioProgreso(
    actividades: List<ActividadFormativa>
): Double {

    if (actividades.isEmpty()) {
        return 0.0
    }

    return actividades
        .map { it.progreso }
        .average()
}

fun buscarPorTitulo(
    actividades: List<ActividadFormativa>,
    texto: String
): List<ActividadFormativa> {

    val textoBuscado = texto.trim()

    return actividades.filter {
        it.titulo.contains(
            textoBuscado,
            ignoreCase = true
        )
    }
}