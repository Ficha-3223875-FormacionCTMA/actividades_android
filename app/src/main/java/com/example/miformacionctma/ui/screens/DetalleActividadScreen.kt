package com.example.miformacionctma.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.miformacionctma.domain.ActividadFormativa

@Composable
fun DetalleActividadScreen(
    actividad: ActividadFormativa?,
    onVolver: () -> Unit,
    onActualizar: (String, String) -> Unit = { _, _ -> },
    onEliminar: () -> Unit = {}
) {
    if (actividad == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("La actividad no existe.")

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Button(onClick = onVolver) {
                Text("Volver")
            }
        }

        return
    }

    var titulo by remember {
        mutableStateOf(actividad.titulo)
    }

    var descripcion by remember {
        mutableStateOf(
            actividad.descripcion ?: ""
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("Editar actividad")

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        OutlinedTextField(
            value = titulo,
            onValueChange = {
                titulo = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Título")
            },
            singleLine = true
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = descripcion,
            onValueChange = {
                descripcion = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Descripción")
            },
            minLines = 4
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Button(
            onClick = {
                onActualizar(
                    titulo.trim(),
                    descripcion.trim()
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar cambios")
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Button(
            onClick = onEliminar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Eliminar actividad")
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Button(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }
    }
}