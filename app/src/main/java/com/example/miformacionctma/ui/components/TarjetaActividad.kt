package com.example.miformacionctma.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.miformacionctma.domain.ActividadFormativa
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics

@Composable
fun TarjetaActividad(
    actividad: ActividadFormativa,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = actividad.titulo,
                style = MaterialTheme.typography.titleLarge
            )

            actividad.descripcion?.let { descripcion ->
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = descripcion,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Progreso: ${actividad.progreso}%",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            LinearProgressIndicator(
                progress = { actividad.progreso / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics {
                        contentDescription =
                            "Progreso de ${actividad.titulo}: ${actividad.progreso} por ciento"
                    }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Días restantes: ${actividad.diasRestantes}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Prioridad: ${actividad.prioridad.name.lowercase()}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun TarjetaActividadPreview() {
    TarjetaActividad(
        actividad = ActividadFormativa(
            id = 1L,
            titulo = "Actividad de formación",
            descripcion = "Ejemplo de actividad para la vista previa.",
            progreso = 60,
            diasRestantes = 5,
            prioridad = com.example.miformacionctma.domain.Prioridad.MEDIA
        )
    )
}