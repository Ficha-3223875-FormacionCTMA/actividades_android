package com.example.miformacionctma.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

data class FormularioActividadUiState(
    val titulo: String = "",
    val descripcion: String = "",
    val fechaEntrega: String = "",
    val prioridad: Prioridad = Prioridad.MEDIA,
    val progreso: Int = 0,
    val tituloError: String? = null,
    val descripcionError: String? = null,
    val fechaError: String? = null,
    val progresoError: String? = null,
    val intentoGuardar: Boolean = false,
    val guardando: Boolean = false
) {
    val puedeGuardar: Boolean
        get() = titulo.trim().length in 3..80 &&
            descripcion.length <= 240 &&
            fechaValida(fechaEntrega) &&
            progreso in 0..100
}

fun validarTitulo(valor: String, mostrarVacio: Boolean): String? {
    val limpio = valor.trim()
    return when {
        limpio.isEmpty() && mostrarVacio -> "Escribe un título"
        limpio.isNotEmpty() && limpio.length < 3 -> "Usa al menos 3 caracteres"
        limpio.length > 80 -> "Usa máximo 80 caracteres"
        else -> null
    }
}

fun validarFecha(valor: String, mostrarVacio: Boolean): String? {
    if (valor.isBlank()) return if (mostrarVacio) "Escribe una fecha" else null
    return try {
        val fecha = LocalDate.parse(valor, DateTimeFormatter.ISO_LOCAL_DATE)
        if (fecha.isBefore(LocalDate.now())) "La fecha no puede ser anterior a hoy" else null
    } catch (_: DateTimeParseException) {
        "Usa el formato AAAA-MM-DD"
    }
}

fun fechaValida(valor: String): Boolean = validarFecha(valor, true) == null

fun validarFormulario(s: FormularioActividadUiState): FormularioActividadUiState = s.copy(
    intentoGuardar = true,
    tituloError = validarTitulo(s.titulo, true),
    descripcionError = if (s.descripcion.length > 240) "Usa máximo 240 caracteres" else null,
    fechaError = validarFecha(s.fechaEntrega, true),
    progresoError = if (s.progreso !in 0..100) "El progreso debe estar entre 0 y 100" else null
)
