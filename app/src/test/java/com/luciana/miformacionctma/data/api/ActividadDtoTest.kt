package com.luciana.miformacionctma.data.api

import com.luciana.miformacionctma.domain.Prioridad
import kotlinx.serialization.json.JsonPrimitive
import org.junit.Assert.assertEquals
import org.junit.Test

class ActividadDtoTest {

    @Test
    fun contratoCompleto_seMapeaAlDominio() {
        val actividad = ActividadDto(
            id = JsonPrimitive("CTMA-007"),
            titulo = "Actividad desde API local",
            descripcion = "Descripción de prueba",
            progreso = 75,
            prioridad = "ALTA",
            competenciaId = "ADSO-07",
            fechaLimite = "30/09/2026",
            completada = false,
            actualizadoEn = "2026-09-23T10:00:00Z"
        ).toDomain()

        assertEquals("Actividad desde API local", actividad.titulo)
        assertEquals("Descripción de prueba", actividad.descripcion)
        assertEquals(75, actividad.progreso)
        assertEquals(Prioridad.ALTA, actividad.prioridad)
        assertEquals("30/09/2026", actividad.fecha)
        assertEquals(false, actividad.resuelto)
    }

    @Test
    fun progreso_fueraDeRango_seLimita() {
        val actividad = ActividadDto(
            id = JsonPrimitive("CTMA-008"),
            titulo = "Actividad límite",
            descripcion = "Prueba",
            progreso = 140,
            prioridad = "MEDIA",
            competenciaId = "ADSO-08",
            fechaLimite = "30/09/2026",
            completada = false,
            actualizadoEn = "2026-09-23T10:00:00Z"
        ).toDomain()

        assertEquals(100, actividad.progreso)
        assertEquals(7, actividad.diasRestantes)
    }
}
