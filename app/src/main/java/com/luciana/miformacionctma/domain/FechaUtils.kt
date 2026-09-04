package com.luciana.miformacionctma.domain

fun calcularDiasRestantes(fecha: String): Int {

    if (fecha.isBlank()) {
        return 0
    }

    return try {

        val partes = fecha.split("/")

        if (partes.size != 3) {
            return 0
        }

        val dia = partes[0].toInt()
        val mes = partes[1].toInt()
        val anio = partes[2].toInt()

        val fechaObjetivo = java.util.Calendar.getInstance()

        fechaObjetivo.set(
            anio,
            mes - 1,
            dia,
            0,
            0,
            0
        )

        fechaObjetivo.set(
            java.util.Calendar.MILLISECOND,
            0
        )

        val fechaActual = java.util.Calendar.getInstance()

        fechaActual.set(
            java.util.Calendar.HOUR_OF_DAY,
            0
        )

        fechaActual.set(
            java.util.Calendar.MINUTE,
            0
        )

        fechaActual.set(
            java.util.Calendar.SECOND,
            0
        )

        fechaActual.set(
            java.util.Calendar.MILLISECOND,
            0
        )

        val diferencia =
            fechaObjetivo.timeInMillis -
                    fechaActual.timeInMillis

        (diferencia /
                (1000L * 60L * 60L * 24L)
                ).toInt()

    } catch (e: Exception) {

        0
    }
}