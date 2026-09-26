package com.luciana.miformacionctma.data.api

import org.junit.Assert.assertEquals
import org.junit.Test

class ActividadApiContractTest {

    @Test
    fun `dto de actividad se convierte a dominio`() {
        val dto = ActividadDto(
            id = "ACT-007",
            titulo = "Prueba API",
            descripcion = "Actividad de prueba",
            aprendiz = "APR-01",
            estado = "COMPLETADA",
            createdAt = "2026-09-25T10:00:00"
        )

        val domain = dto.toDomain()

        assertEquals(7L, domain.id)
        assertEquals("Prueba API", domain.titulo)
        assertEquals(100, domain.progreso)
        assertEquals(true, domain.resuelto)
    }
}
