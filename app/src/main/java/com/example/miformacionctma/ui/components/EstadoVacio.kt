package com.example.miformacionctma.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EventNote
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun EstadoVacio(onCrear: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = Icons.Outlined.EventNote,
            contentDescription = "Sin actividades registradas",
            modifier = Modifier.size(56.dp).semantics { contentDescription = "Ilustración de actividades vacías" }
        )
        Text("Aún no tienes actividades", style = MaterialTheme.typography.titleMedium)
        Text("Agrega tu primera actividad formativa para comenzar.", textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        Button(onClick = onCrear) { Text("Agregar actividad") }
    }
}
