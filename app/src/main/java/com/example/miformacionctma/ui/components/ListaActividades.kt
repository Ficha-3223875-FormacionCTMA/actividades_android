package com.example.miformacionctma.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.miformacionctma.model.ActividadFormativa

@Composable
fun ListaActividades(actividades: List<ActividadFormativa>, onActividadClick: (Long) -> Unit, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier, contentPadding = PaddingValues(vertical = 8.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        items(actividades, key = { it.id }) { actividad ->
            TarjetaActividad(actividad = actividad, onClick = { onActividadClick(actividad.id) })
        }
    }
}
