package com.example.miformacionctma

import com.example.miformacionctma.domain.ActividadFormativa
import com.example.miformacionctma.domain.Prioridad
import com.example.miformacionctma.domain.validarActividad
import com.example.miformacionctma.domain.estadoActividad
import com.example.miformacionctma.domain.promedioProgreso
import com.example.miformacionctma.domain.buscarPorTitulo
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ReglasActividadTest {

    private fun actividad(
        titulo: String = "Kotlin",
        progreso: Int = 50,
        dias: Int = 2
    ) = ActividadFormativa(
        id = 1L,
        titulo = titulo,
        descripcion = "Descripción",
        progreso = progreso,
        diasRestantes = dias,
        prioridad = Prioridad.MEDIA
    )

    @Test
    fun tituloVacioEsInvalido() {
        assertTrue(
            validarActividad(
                titulo = " ",
                progreso = 50
            ).isNotEmpty()
        )
    }

    @Test
    fun tituloDosCaracteresEsInvalido() {
        assertTrue(
            validarActividad(
                titulo = "AB",
                progreso = 50
            ).isNotEmpty()
        )
    }

    @Test
    fun tituloValidoEsAceptado() {
        assertTrue(
            validarActividad(
                titulo = "Kotlin",
                progreso = 50
            ).isEmpty()
        )
    }

    @Test
    fun progresoFueraDeRangoEsInvalido() {
        assertTrue(
            validarActividad(
                titulo = "Kotlin",
                progreso = 101
            ).isNotEmpty()
        )
    }

    @Test
    fun progresoCienEsCompletada() {
        assertEquals(
            "COMPLETADA",
            estadoActividad(
                progreso = 100,
                diasRestantes = 2
            ).name
        )
    }

    @Test
    fun actividadPasadaIncompletaEsVencida() {
        assertEquals(
            "VENCIDA",
            estadoActividad(
                progreso = 20,
                diasRestantes = -1
            ).name
        )
    }

    @Test
    fun listaVaciaPromedioCero() {
        assertEquals(
            0.0,
            promedioProgreso(emptyList()),
            0.0
        )
    }

    @Test
    fun busquedaIgnoraMayusculas() {
        assertEquals(
            1,
            buscarPorTitulo(
                listOf(
                    actividad(
                        titulo = "Aprender Kotlin"
                    )
                ),
                "kotlin"
            ).size
        )
    }
}