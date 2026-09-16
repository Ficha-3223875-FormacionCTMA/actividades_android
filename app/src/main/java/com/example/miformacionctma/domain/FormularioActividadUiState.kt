package com.example.miformacionctma.domain

data class FormularioActividadUiState(
    val titulo: String = "",
    val descripcion: String = "",
    val errorTitulo: String? = null,
    val errorDescripcion: String? = null,
    val intentoGuardar: Boolean = false
) {
    val puedeGuardar: Boolean
        get() = titulo.trim().length in 3..80 &&
                descripcion.length <= 240
}