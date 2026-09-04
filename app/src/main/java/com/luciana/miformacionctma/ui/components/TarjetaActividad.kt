package com.luciana.miformacionctma.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.luciana.miformacionctma.domain.ActividadFormativa
import com.luciana.miformacionctma.domain.EstadoActividad
import com.luciana.miformacionctma.domain.Prioridad
import com.luciana.miformacionctma.domain.estadoActividad

@Composable
fun TarjetaActividad(
    actividad: ActividadFormativa,
    onActividadClick: (ActividadFormativa) -> Unit = {}
) {

    val estado = estadoActividad(actividad)

    val textoEstado = when (estado) {
        EstadoActividad.PENDIENTE -> "Pendiente"
        EstadoActividad.EN_PROCESO -> "En proceso"
        EstadoActividad.COMPLETADA -> "Completada"
        EstadoActividad.VENCIDA -> "Vencida"
    }

    val textoPrioridad = when (actividad.prioridad) {
        Prioridad.BAJA -> "Baja"
        Prioridad.MEDIA -> "Media"
        Prioridad.ALTA -> "Alta"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .semantics {
                contentDescription =
                    "${actividad.titulo}. " +
                            "Estado: $textoEstado. " +
                            "Progreso: ${actividad.progreso} por ciento. " +
                            "Prioridad: $textoPrioridad."
            },
        onClick = {
            onActividadClick(actividad)
        }
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = actividad.titulo,
                style = MaterialTheme.typography.titleMedium
            )

            actividad.descripcion?.let { descripcion ->

                Text(
                    text = descripcion,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Estado: $textoEstado",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "Prioridad: $textoPrioridad",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Text(
                text = "Progreso: ${actividad.progreso}%",
                style = MaterialTheme.typography.bodyMedium
            )

            LinearProgressIndicator(
                progress = {
                    actividad.progreso.coerceIn(0, 100) / 100f
                },
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = when {
                    actividad.diasRestantes < 0 ->
                        "Actividad vencida"

                    actividad.diasRestantes == 0 ->
                        "Vence hoy"

                    actividad.diasRestantes == 1 ->
                        "Falta 1 día"

                    else ->
                        "Faltan ${actividad.diasRestantes} días"
                },
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
