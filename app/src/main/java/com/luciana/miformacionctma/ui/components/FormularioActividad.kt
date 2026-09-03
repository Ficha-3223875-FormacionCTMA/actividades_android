package com.luciana.miformacionctma.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.luciana.miformacionctma.domain.Prioridad
import com.luciana.miformacionctma.ui.state.FormularioActividadUiState

@Composable
fun FormularioActividad(
    uiState: FormularioActividadUiState,
    onTituloChange: (String) -> Unit,
    onDescripcionChange: (String) -> Unit,
    onFechaChange: (String) -> Unit,
    onPrioridadChange: (Prioridad) -> Unit,
    onProgresoChange: (String) -> Unit,
    onGuardar: () -> Unit,
    onCancelar: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        OutlinedTextField(
            value = uiState.titulo,
            onValueChange = onTituloChange,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Título")
            },
            isError = uiState.tituloError != null,
            supportingText = {
                uiState.tituloError?.let {
                    Text(it)
                }
            },
            singleLine = true
        )

        OutlinedTextField(
            value = uiState.descripcion,
            onValueChange = onDescripcionChange,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Descripción")
            },
            isError = uiState.descripcionError != null,
            supportingText = {
                uiState.descripcionError?.let {
                    Text(it)
                }
            },
            minLines = 3,
            maxLines = 5
        )

        OutlinedTextField(
            value = uiState.fecha,
            onValueChange = onFechaChange,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Fecha")
            },
            isError = uiState.fechaError != null,
            supportingText = {
                uiState.fechaError?.let {
                    Text(it)
                }
            },
            singleLine = true,
            placeholder = {
                Text("dd/MM/yyyy")
            }
        )

        OutlinedTextField(
            value = uiState.progreso,
            onValueChange = onProgresoChange,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Progreso (%)")
            },
            isError = uiState.progresoError != null,
            supportingText = {
                uiState.progresoError?.let {
                    Text(it)
                }
            },
            singleLine = true
        )

        Text(
            text = "Prioridad: ${uiState.prioridad.name}"
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            TextButton(
                onClick = {
                    onPrioridadChange(Prioridad.BAJA)
                }
            ) {
                Text("Baja")
            }

            TextButton(
                onClick = {
                    onPrioridadChange(Prioridad.MEDIA)
                }
            ) {
                Text("Media")
            }

            TextButton(
                onClick = {
                    onPrioridadChange(Prioridad.ALTA)
                }
            ) {
                Text("Alta")
            }
        }

        Button(
            onClick = onGuardar,
            modifier = Modifier.fillMaxWidth(),
            enabled = uiState.puedeGuardar
        ) {
            Text("Guardar")
        }

        TextButton(
            onClick = onCancelar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancelar")
        }
    }
}