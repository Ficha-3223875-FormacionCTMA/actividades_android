package com.example.miformacionctma.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

private val FORMATO_FECHA: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE

fun validarActividad(actividad: ActividadFormativa): List<String> {
    val errores = mutableListOf<String>()
    val titulo = actividad.titulo.trim()
    if (titulo.isEmpty()) errores += "El título es obligatorio"
    else if (titulo.length !in 3..80) errores += "El título debe tener entre 3 y 80 caracteres"
    if (actividad.descripcion.orEmpty().length > 240) errores += "La descripción no puede superar 240 caracteres"
    if (actividad.progreso !in 0..100) errores += "El progreso debe estar entre 0 y 100"
    if (actividad.fechaEntrega.isNotBlank()) {
        try {
            val fecha = LocalDate.parse(actividad.fechaEntrega, FORMATO_FECHA)
            if (fecha.isBefore(LocalDate.now())) errores += "La fecha de entrega no puede ser anterior a hoy"
        } catch (_: DateTimeParseException) {
            errores += "La fecha debe usar el formato AAAA-MM-DD"
        }
    }
    return errores
}

fun estadoActividad(actividad: ActividadFormativa): String = when {
    actividad.progreso >= 100 -> "COMPLETADA"
    actividad.diasRestantes < 0 -> "VENCIDA"
    actividad.progreso > 0 -> "EN PROCESO"
    else -> "PENDIENTE"
}

fun actividadesUrgentes(actividades: List<ActividadFormativa>): List<ActividadFormativa> =
    actividades.filter { it.progreso < 100 && it.diasRestantes <= 2 }

fun promedioProgreso(actividades: List<ActividadFormativa>): Double =
    if (actividades.isEmpty()) 0.0 else actividades.map { it.progreso }.average()

fun buscarPorTitulo(actividades: List<ActividadFormativa>, texto: String): List<ActividadFormativa> {
    val busqueda = texto.trim()
    if (busqueda.isEmpty()) return actividades
    return actividades.filter { it.titulo.contains(busqueda, ignoreCase = true) }
}

fun ordenarActividades(actividades: List<ActividadFormativa>): List<ActividadFormativa> =
    actividades.sortedWith(
        compareBy<ActividadFormativa> { estadoActividad(it) != "VENCIDA" }
            .thenBy { when (it.prioridad) { Prioridad.ALTA -> 0; Prioridad.MEDIA -> 1; Prioridad.BAJA -> 2 } }
            .thenBy { it.diasRestantes }
    )

fun diasHasta(fecha: String): Int? = try {
    LocalDate.parse(fecha, FORMATO_FECHA).toEpochDay().minus(LocalDate.now().toEpochDay()).toInt()
} catch (_: Exception) { null }
