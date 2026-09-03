package com.example.miformacionctma.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.miformacionctma.model.*

@Composable
fun FormularioActividad(
    uiState: FormularioActividadUiState,
    onTituloChange: (String) -> Unit,
    onDescripcionChange: (String) -> Unit,
    onFechaChange: (String) -> Unit,
    onPrioridadChange: (Prioridad) -> Unit,
    onProgresoChange: (Int) -> Unit,
    onGuardarClick: () -> Unit,
    onCancelar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Text(if (uiState.titulo.isBlank()) "Nueva actividad" else "Editar actividad", style = MaterialTheme.typography.headlineSmall)
        Text("Completa la información de la actividad formativa.")
        OutlinedTextField(value = uiState.titulo, onValueChange = { onTituloChange(it.take(80)) }, label = { Text("Título") }, supportingText = { Text(uiState.tituloError ?: "${uiState.titulo.length}/80") }, isError = uiState.tituloError != null, singleLine = true, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = uiState.descripcion, onValueChange = { onDescripcionChange(it.take(240)) }, label = { Text("Descripción (opcional)") }, supportingText = { Text(uiState.descripcionError ?: "${uiState.descripcion.length}/240") }, isError = uiState.descripcionError != null, minLines = 3, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = uiState.fechaEntrega, onValueChange = { onFechaChange(it.take(10)) }, label = { Text("Fecha de entrega") }, placeholder = { Text("AAAA-MM-DD") }, supportingText = { Text(uiState.fechaError ?: "Ejemplo: 2026-12-15") }, isError = uiState.fechaError != null, singleLine = true, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
        Text("Prioridad", style = MaterialTheme.typography.labelLarge)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Prioridad.values().forEach { p -> FilterChip(selected = uiState.prioridad == p, onClick = { onPrioridadChange(p) }, label = { Text(p.name.lowercase().replaceFirstChar { it.uppercase() }) }) }
        }
        Text("Progreso: ${uiState.progreso}%", style = MaterialTheme.typography.labelLarge)
        Slider(value = uiState.progreso.toFloat(), onValueChange = { onProgresoChange(it.toInt()) }, valueRange = 0f..100f, steps = 99, modifier = Modifier.fillMaxWidth())
        uiState.progresoError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            OutlinedButton(onClick = onCancelar, modifier = Modifier.weight(1f)) { Text("Cancelar") }
            Button(onClick = onGuardarClick, enabled = !uiState.guardando, modifier = Modifier.weight(1f)) { Text(if (uiState.guardando) "Guardando…" else "Guardar") }
        }
    }
}

@Composable
fun FormularioRoute(
    actividad: ActividadFormativa?,
    onGuardar: (ActividadFormativa) -> Unit,
    onCancelar: () -> Unit
) {
    var titulo by rememberSaveable(actividad?.id) { mutableStateOf(actividad?.titulo ?: "") }
    var descripcion by rememberSaveable(actividad?.id) { mutableStateOf(actividad?.descripcion ?: "") }
    var fecha by rememberSaveable(actividad?.id) { mutableStateOf(actividad?.fechaEntrega ?: "") }
    var prioridadName by rememberSaveable(actividad?.id) { mutableStateOf((actividad?.prioridad ?: Prioridad.MEDIA).name) }
    var progreso by rememberSaveable(actividad?.id) { mutableIntStateOf(actividad?.progreso ?: 0) }
    var intento by rememberSaveable(actividad?.id) { mutableStateOf(false) }
    var guardando by rememberSaveable(actividad?.id) { mutableStateOf(false) }

    val stateBase = FormularioActividadUiState(titulo, descripcion, fecha, Prioridad.valueOf(prioridadName), progreso, intentoGuardar = intento, guardando = guardando)
    val state = if (intento) validarFormulario(stateBase) else stateBase

    FormularioActividad(
        uiState = state,
        onTituloChange = { titulo = it; if (intento) intento = false },
        onDescripcionChange = { descripcion = it },
        onFechaChange = { fecha = it },
        onPrioridadChange = { prioridadName = it.name },
        onProgresoChange = { progreso = it },
        onGuardarClick = {
            val validado = validarFormulario(stateBase)
            intento = true
            if (validado.puedeGuardar && !guardando) {
                guardando = true
                val id = actividad?.id ?: System.currentTimeMillis()
                onGuardar(ActividadFormativa(id, titulo.trim(), descripcion.trim().ifBlank { null }, progreso, diasHasta(fecha) ?: 0, Prioridad.valueOf(prioridadName), fecha))
                guardando = false
            }
        },
        onCancelar = onCancelar
    )
}
