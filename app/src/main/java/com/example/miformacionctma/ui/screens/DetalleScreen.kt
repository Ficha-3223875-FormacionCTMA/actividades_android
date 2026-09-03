package com.example.miformacionctma.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.miformacionctma.model.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleScreen(actividad: ActividadFormativa?, onBack: () -> Unit, onEditar: () -> Unit) {
    Scaffold(topBar = { TopAppBar(title = { Text("Detalle") }, navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver") } }, actions = { if (actividad != null) IconButton(onClick = onEditar) { Icon(Icons.Filled.Edit, "Editar") } }) }) { padding ->
        if (actividad == null) {
            Column(Modifier.fillMaxSize().padding(padding).padding(24.dp), verticalArrangement = Arrangement.Center) {
                Text("Actividad no encontrada", style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(8.dp)); Text("El identificador recibido no corresponde a una actividad.")
                Spacer(Modifier.height(16.dp)); Button(onClick = onBack) { Text("Volver a la lista") }
            }
        } else {
            Column(Modifier.fillMaxSize().padding(padding).padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Text(actividad.titulo, style = MaterialTheme.typography.headlineSmall)
                Text(actividad.descripcion ?: "Sin descripción")
                HorizontalDivider()
                Text("Estado: ${estadoActividad(actividad)}", style = MaterialTheme.typography.titleMedium)
                Text("Prioridad: ${actividad.prioridad.name.lowercase().replaceFirstChar { it.uppercase() }}")
                if (actividad.fechaEntrega.isNotBlank()) Text("Fecha de entrega: ${actividad.fechaEntrega}")
                Text("Progreso: ${actividad.progreso}%")
                LinearProgressIndicator(progress = { actividad.progreso.coerceIn(0,100) / 100f }, modifier = Modifier.fillMaxWidth())
                Button(onClick = onEditar, modifier = Modifier.fillMaxWidth()) { Text("Editar actividad") }
            }
        }
    }
}
