package com.example.miformacionctma.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.miformacionctma.model.*

@Composable
fun TarjetaActividad(actividad: ActividadFormativa, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val estado = estadoActividad(actividad)
    val prioridad = actividad.prioridad.name.lowercase().replaceFirstChar { it.uppercase() }
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().semantics {
            contentDescription = "Actividad ${actividad.titulo}. Estado $estado. Prioridad $prioridad. Progreso ${actividad.progreso} por ciento."
        },
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(actividad.titulo, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                AssistChip(onClick = {}, enabled = false, label = { Text("${actividad.progreso}%") })
            }
            Text(actividad.descripcion ?: "Sin descripción", style = MaterialTheme.typography.bodyMedium)
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Estado: $estado", style = MaterialTheme.typography.labelLarge)
                Text("Prioridad: $prioridad", style = MaterialTheme.typography.labelLarge)
            }
            if (actividad.fechaEntrega.isNotBlank()) Text("Entrega: ${actividad.fechaEntrega}")
            LinearProgressIndicator(
                progress = { actividad.progreso.coerceIn(0, 100) / 100f },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
