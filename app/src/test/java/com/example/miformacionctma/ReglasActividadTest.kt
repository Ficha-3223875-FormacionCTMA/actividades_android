package com.example.miformacionctma

import com.example.miformacionctma.model.*
import org.junit.Assert.*
import org.junit.Test

class ReglasActividadTest {
    private fun actividad(titulo: String = "Kotlin", progreso: Int = 50, dias: Int = 2) =
        ActividadFormativa(1, titulo, "Descripción", progreso, dias, Prioridad.MEDIA, "2099-12-15")

    @Test fun tituloVacioEsInvalido() = assertTrue(validarActividad(actividad(" ")).isNotEmpty())
    @Test fun tituloDosCaracteresEsInvalido() = assertTrue(validarActividad(actividad("AB")).isNotEmpty())
    @Test fun tituloValidoEsAceptado() = assertTrue(validarActividad(actividad("Kotlin")).isEmpty())
    @Test fun progresoFueraDeRangoEsInvalido() = assertTrue(validarActividad(actividad(progreso = 101)).isNotEmpty())
    @Test fun progresoCienEsCompletada() = assertEquals("COMPLETADA", estadoActividad(actividad(progreso = 100)))
    @Test fun actividadPasadaIncompletaEsVencida() = assertEquals("VENCIDA", estadoActividad(actividad(progreso = 20, dias = -1)))
    @Test fun listaVaciaPromedioCero() = assertEquals(0.0, promedioProgreso(emptyList()), 0.0)
    @Test fun busquedaIgnoraMayusculas() = assertEquals(1, buscarPorTitulo(listOf(actividad("Aprender Kotlin")), "kotlin").size)
}
