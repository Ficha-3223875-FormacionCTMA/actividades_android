package com.luciana.miformacionctma.domain

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import org.junit.Assert.assertNull
import org.junit.Assert.assertNotNull
import org.junit.Test

class ReglasActividadFechaTest {
    @Test
    fun fechaValida_futura_noGeneraError() {
        val futura = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        assertNull(validarFecha(futura))
    }

    @Test
    fun fechaInvalida_generaError() {
        assertNotNull(validarFecha("31/02/2026"))
    }

    @Test
    fun fechaAnterior_generaError() {
        val anterior = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        assertNotNull(validarFecha(anterior))
    }
}
