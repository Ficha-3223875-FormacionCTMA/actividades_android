package com.luciana.miformacionctma.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.luciana.miformacionctma.domain.ActividadFormativa
import com.luciana.miformacionctma.domain.estadoActividad

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDetalleActividad(
    actividad: ActividadFormativa?,
    onVolver: () -> Unit,
    onEditar: () -> Unit = {},
    onEliminar: () -> Unit = {}
) {

    var mostrarDialogoEliminar by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Detalle de actividad")
                }
            )
        }
    ) { paddingValues ->

        if (actividad == null) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Text(
                    text = "Actividad no encontrada",
                    style = MaterialTheme.typography.headlineSmall
                )

                Text(
                    text = "No existe una actividad con el identificador solicitado."
                )

                Button(
                    onClick = onVolver
                ) {
                    Text("Volver")
                }
            }

        } else {

            val estado = estadoActividad(actividad)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Text(
                    text = actividad.titulo,
                    style = MaterialTheme.typography.headlineSmall
                )

                Text(
                    text = actividad.descripcion
                        ?: "Sin descripción"
                )

                Text(
                    text = "Progreso: ${actividad.progreso}%"
                )

                Text(
                    text = "Días restantes: ${actividad.diasRestantes}"
                )

                Text(
                    text = "Prioridad: ${actividad.prioridad.name}"
                )

                Text(
                    text = "Estado: ${estado.name}"
                )

                // EDITAR
                Button(
                    onClick = onEditar
                ) {
                    Text("Editar actividad")
                }

                // ELIMINAR
                Button(
                    onClick = {
                        mostrarDialogoEliminar = true
                    }
                ) {
                    Text("Eliminar actividad")
                }

                // VOLVER
                Button(
                    onClick = onVolver
                ) {
                    Text("Volver")
                }
            }

            // DIÁLOGO DE CONFIRMACIÓN
            if (mostrarDialogoEliminar) {

                AlertDialog(
                    onDismissRequest = {
                        mostrarDialogoEliminar = false
                    },

                    title = {
                        Text("Eliminar actividad")
                    },

                    text = {
                        Text(
                            "¿Estás segura de que deseas eliminar \"${actividad.titulo}\"?"
                        )
                    },

                    confirmButton = {
                        TextButton(
                            onClick = {
                                mostrarDialogoEliminar = false
                                onEliminar()
                            }
                        ) {
                            Text("Eliminar")
                        }
                    },

                    dismissButton = {
                        TextButton(
                            onClick = {
                                mostrarDialogoEliminar = false
                            }
                        ) {
                            Text("Cancelar")
                        }
                    }
                )
            }
        }
    }
}