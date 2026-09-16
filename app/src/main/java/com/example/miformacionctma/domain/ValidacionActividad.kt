package com.example.miformacionctma.domain

fun validarTitulo(titulo: String): String? {
    val texto = titulo.trim()

    return when {
        texto.isEmpty() -> "El título es obligatorio."
        texto.length < 3 -> "El título debe tener mínimo 3 caracteres."
        texto.length > 80 -> "El título no puede superar 80 caracteres."
        else -> null
    }
}

fun validarDescripcion(descripcion: String): String? {
    return if (descripcion.length > 240) {
        "La descripción no puede superar 240 caracteres."
    } else {
        null
    }
}

fun validarFormulario(
    titulo: String,
    descripcion: String
): Pair<String?, String?> {
    return Pair(
        validarTitulo(titulo),
        validarDescripcion(descripcion)
    )
}