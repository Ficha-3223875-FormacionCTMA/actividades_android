package com.luciana.miformacionctma.domain

fun validarActividad(
    titulo: String,
    progreso: Int
): List<String> {

    val errores = mutableListOf<String>()

    if (titulo.isBlank()) {
        errores.add("El título es obligatorio")
    }

    if (progreso !in 0..100) {
        errores.add("El progreso debe estar entre 0 y 100")
    }

    return errores
}

/*
 * Determina el estado de una actividad.
 */
fun estadoActividad(
    actividad: ActividadFormativa
): EstadoActividad {

    return when {

        actividad.progreso == 100 ->
            EstadoActividad.COMPLETADA

        actividad.diasRestantes < 0 ->
            EstadoActividad.VENCIDA

        actividad.progreso > 0 ->
            EstadoActividad.EN_PROCESO

        else ->
            EstadoActividad.PENDIENTE
    }
}

/*
 * Devuelve las actividades urgentes.
 */
fun actividadesUrgentes(
    actividades: List<ActividadFormativa>
): List<ActividadFormativa> {

    return actividades.filter {

        it.progreso < 100 &&
                it.diasRestantes <= 2

    }
}

/*
 * Calcula el promedio del progreso.
 */
fun promedioProgreso(
    actividades: List<ActividadFormativa>
): Double {

    if (actividades.isEmpty()) {
        return 0.0
    }

    return actividades
        .map { it.progreso }
        .average()
}

/*
 * Busca actividades por título.
 */
fun buscarPorTitulo(
    actividades: List<ActividadFormativa>,
    texto: String
): List<ActividadFormativa> {

    val busqueda = texto.trim()

    if (busqueda.isEmpty()) {
        return emptyList()
    }

    return actividades.filter {

        it.titulo.contains(
            busqueda,
            ignoreCase = true
        )

    }
}

/*
 * Ordena:
 * 1. Vencidas
 * 2. Prioridad Alta
 * 3. Menor número de días
 */
fun ordenarActividades(
    actividades: List<ActividadFormativa>
): List<ActividadFormativa> {

    return actividades.sortedWith(

        compareByDescending<ActividadFormativa> {

            estadoActividad(it) == EstadoActividad.VENCIDA

        }

            .thenByDescending {

                when (it.prioridad) {

                    Prioridad.ALTA -> 3
                    Prioridad.MEDIA -> 2
                    Prioridad.BAJA -> 1
                }

            }

            .thenBy {

                it.diasRestantes

            }

    )
}

/*
 * Genera un resumen de las actividades.
 */
fun generarResumen(
    actividades: List<ActividadFormativa>
): String {

    val promedio = promedioProgreso(actividades)

    val completadas = actividades.count {

        estadoActividad(it) == EstadoActividad.COMPLETADA

    }

    val vencidas = actividades.count {

        estadoActividad(it) == EstadoActividad.VENCIDA

    }

    val urgentes = actividadesUrgentes(actividades).size

    return """
Total de actividades: ${actividades.size}
Promedio: ${"%.1f".format(promedio)} %
Completadas: $completadas
Vencidas: $vencidas
Urgentes: $urgentes
""".trimIndent()

}
