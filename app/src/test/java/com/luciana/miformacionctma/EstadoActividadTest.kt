package com.luciana.miformacionctma

import com.luciana.miformacionctma.domain.ActividadFormativa
import com.luciana.miformacionctma.domain.EstadoActividad
import com.luciana.miformacionctma.domain.Prioridad
import com.luciana.miformacionctma.domain.estadoActividad
import org.junit.Assert.assertEquals
import org.junit.Test

class EstadoActividadTest {

    @Test
    fun estadoActividad_progreso0_devuelvePendiente() {

        val actividad = ActividadFormativa(
            id = 1L,
            titulo = "Actividad pendiente",
            descripcion = null,
            progreso = 0,
            diasRestantes = 5,
            prioridad = Prioridad.BAJA
        )

        val estadoObtenido = estadoActividad(actividad)

        assertEquals(
            EstadoActividad.PENDIENTE,
            estadoObtenido
        )
    }

    @Test
    fun estadoActividad_progreso50_devuelveEnProceso() {

        val actividad = ActividadFormativa(
            id = 2L,
            titulo = "Actividad en proceso",
            descripcion = null,
            progreso = 50,
            diasRestantes = 5,
            prioridad = Prioridad.MEDIA
        )

        val estadoObtenido = estadoActividad(actividad)

        assertEquals(
            EstadoActividad.EN_PROCESO,
            estadoObtenido
        )
    }

    @Test
    fun estadoActividad_progreso100_devuelveCompletada() {

        val actividad = ActividadFormativa(
            id = 3L,
            titulo = "Actividad completada",
            descripcion = null,
            progreso = 100,
            diasRestantes = 0,
            prioridad = Prioridad.ALTA
        )

        val estadoObtenido = estadoActividad(actividad)

        assertEquals(
            EstadoActividad.COMPLETADA,
            estadoObtenido
        )
    }
}