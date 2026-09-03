package com.luciana.miformacionctma.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.luciana.miformacionctma.domain.Prioridad
import com.luciana.miformacionctma.domain.validarDescripcion
import com.luciana.miformacionctma.domain.validarProgreso
import com.luciana.miformacionctma.domain.validarTitulo
import com.luciana.miformacionctma.ui.components.FormularioActividad
import com.luciana.miformacionctma.ui.state.FormularioActividadUiState

@Composable
fun PantallaEditarActividad(
    actividadId: Long,
    tituloInicial: String,
    descripcionInicial: String,
    fechaInicial: String,
    prioridadInicial: Prioridad,
    progresoInicial: Int,
    onGuardar: (FormularioActividadUiState) -> Unit,
    onCancelar: () -> Unit
) {

    var titulo by rememberSaveable {
        mutableStateOf(tituloInicial)
    }

    var descripcion by rememberSaveable {
        mutableStateOf(descripcionInicial)
    }

    var fecha by rememberSaveable {
        mutableStateOf(fechaInicial)
    }

    var prioridadNombre by rememberSaveable {
        mutableStateOf(prioridadInicial.name)
    }

    var progreso by rememberSaveable {
        mutableStateOf(progresoInicial.toString())
    }

    var intentoGuardar by rememberSaveable {
        mutableStateOf(false)
    }

    val prioridad = Prioridad.valueOf(prioridadNombre)

    val tituloError =
        if (intentoGuardar) {
            validarTitulo(titulo)
        } else {
            null
        }

    val descripcionError =
        if (intentoGuardar) {
            validarDescripcion(descripcion)
        } else {
            null
        }

    val progresoError =
        if (intentoGuardar) {
            validarProgreso(progreso)
        } else {
            null
        }

    val uiState = FormularioActividadUiState(
        titulo = titulo,
        descripcion = descripcion,
        fecha = fecha,
        prioridad = prioridad,
        progreso = progreso,
        tituloError = tituloError,
        descripcionError = descripcionError,
        progresoError = progresoError,
        intentoGuardar = intentoGuardar
    )

    FormularioActividad(
        uiState = uiState,

        onTituloChange = { nuevoTitulo ->
            titulo = nuevoTitulo
            intentoGuardar = false
        },

        onDescripcionChange = { nuevaDescripcion ->
            descripcion = nuevaDescripcion
            intentoGuardar = false
        },

        onFechaChange = { nuevaFecha ->
            fecha = nuevaFecha
            intentoGuardar = false
        },

        onPrioridadChange = { nuevaPrioridad ->
            prioridadNombre = nuevaPrioridad.name
        },

        onProgresoChange = { nuevoProgreso ->
            progreso = nuevoProgreso
            intentoGuardar = false
        },

        onGuardar = {

            intentoGuardar = true

            val estadoFinal = FormularioActividadUiState(
                titulo = titulo,
                descripcion = descripcion,
                fecha = fecha,
                prioridad = prioridad,
                progreso = progreso,
                tituloError = validarTitulo(titulo),
                descripcionError = validarDescripcion(descripcion),
                progresoError = validarProgreso(progreso),
                intentoGuardar = true
            )

            if (estadoFinal.puedeGuardar) {
                onGuardar(estadoFinal)
            }
        },

        onCancelar = onCancelar
    )
}