package com.luciana.miformacionctma.ui.state

import com.luciana.miformacionctma.domain.Prioridad
import com.luciana.miformacionctma.domain.validarDescripcion
import com.luciana.miformacionctma.domain.validarProgreso
import com.luciana.miformacionctma.domain.validarTitulo

data class FormularioActividadUiState(
    val titulo: String = "",
    val descripcion: String = "",
    val fecha: String = "",
    val prioridad: Prioridad = Prioridad.MEDIA,
    val progreso: String = "0",

    val tituloError: String? = null,
    val descripcionError: String? = null,
    val fechaError: String? = null,
    val progresoError: String? = null,

    val intentoGuardar: Boolean = false
) {

    val puedeGuardar: Boolean
        get() =
            validarTitulo(titulo) == null &&
                    validarDescripcion(descripcion) == null &&
                    validarProgreso(progreso) == null
}