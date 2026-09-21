package com.example.miformacionctma

import org.junit.Assert.assertEquals
import org.junit.Test

class ActividadDaoTest {

    @Test
    fun pruebaBasica() {

        val titulo = "Actividad de prueba"

        assertEquals(
            "Actividad de prueba",
            titulo
        )
    }
}