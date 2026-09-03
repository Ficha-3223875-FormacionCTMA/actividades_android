package com.example.miformacionctma.model

fun main() {

    val actividad1 = ActividadFormativa(
        id = 1L,
        titulo = "Configurar Android Studio",
        descripcion = "Instalar y configurar el entorno",
        progreso = 80,
        diasRestantes = -1,
        prioridad = Prioridad.ALTA
    )

    val actividad2 = ActividadFormativa(
        id = 2L,
        titulo = "Aprender Kotlin",
        descripcion = "Practicar fundamentos de Kotlin",
        progreso = 50,
        diasRestantes = 2,
        prioridad = Prioridad.MEDIA
    )

    val actividad3 = ActividadFormativa(
        id = 3L,
        titulo = "Crear proyecto Android",
        descripcion = null,
        progreso = 100,
        diasRestantes = -2,
        prioridad = Prioridad.ALTA
    )

    val actividades = listOf(
        actividad1,
        actividad2,
        actividad3
    )

    println("=== ESTADOS ===")

    actividades.forEach {
        println("${it.titulo}: ${estadoActividad(it)}")
    }

    println()
    println("=== ACTIVIDADES URGENTES ===")

    val urgentes = actividadesUrgentes(actividades)

    urgentes.forEach {
        println(it.titulo)
    }

    println()
    println("=== PROMEDIO ===")

    println(promedioProgreso(actividades))

    println()
    println("=== VALIDACIÓN ===")

    val actividadInvalida = ActividadFormativa(
        id = 4L,
        titulo = "",
        descripcion = "Actividad de prueba",
        progreso = 120,
        diasRestantes = 5,
        prioridad = Prioridad.BAJA
    )

    val errores = validarActividad(actividadInvalida)

    errores.forEach {
        println(it)
    }
}