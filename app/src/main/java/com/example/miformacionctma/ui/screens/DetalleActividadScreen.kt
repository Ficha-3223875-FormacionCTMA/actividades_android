package com.example.miformacionctma.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.miformacionctma.domain.ActividadFormativa
import com.example.miformacionctma.domain.Evidencia
import com.example.miformacionctma.domain.validarDescripcion
import com.example.miformacionctma.domain.validarTitulo

@Composable
fun DetalleActividadScreen(
    actividad: ActividadFormativa?,
    evidencias: List<Evidencia> = emptyList(),
    onVolver: () -> Unit,
    onActualizar: (String, String) -> Unit = { _, _ -> },
    onEliminar: () -> Unit = {},
    onAgregarEvidencia: () -> Unit = {}
) {
    if (actividad == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("La actividad no existe.")

            Spacer(modifier = Modifier.height(24.dp))

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
        mutableStateOf(actividad.descripcion ?: "")
    }

    var intentoGuardar by remember {
        mutableStateOf(false)
    }

    val errorTitulo =
        if (intentoGuardar) {
            validarTitulo(titulo)
        } else {
            null
        }

    val errorDescripcion =
        if (intentoGuardar) {
            validarDescripcion(descripcion)
        } else {
            null
        }

    val puedeGuardar =
        validarTitulo(titulo) == null &&
                validarDescripcion(descripcion) == null

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        item {
            Text("Editar actividad")

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = titulo,
                onValueChange = {
                    titulo = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Título")
                },
                isError = errorTitulo != null,
                supportingText = {
                    if (errorTitulo != null) {
                        Text(errorTitulo!!)
                    } else {
                        Text("${titulo.length}/80")
                    }
                },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = descripcion,
                onValueChange = {
                    descripcion = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Descripción")
                },
                minLines = 4,
                isError = errorDescripcion != null,
                supportingText = {
                    if (errorDescripcion != null) {
                        Text(errorDescripcion!!)
                    } else {
                        Text("${descripcion.length}/240")
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    intentoGuardar = true

                    if (puedeGuardar) {
                        onActualizar(
                            titulo.trim(),
                            descripcion.trim()
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar cambios")
            }

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider()

            Spacer(modifier = Modifier.height(16.dp))

            Text("Evidencias")

            Spacer(modifier = Modifier.height(8.dp))
        }

        if (evidencias.isEmpty()) {

            item {
                Text("No hay evidencias fotográficas todavía.")

                Spacer(modifier = Modifier.height(8.dp))
            }

        } else {

            items(
                items = evidencias,
                key = { it.id }
            ) { evidencia ->

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    AsyncImage(
                        model = evidencia.localUri,
                        contentDescription = "Evidencia fotográfica",
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(220.dp),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Estado: ${evidencia.estado}"
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }

        item {

            Button(
                onClick = onAgregarEvidencia,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar evidencia fotográfica")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onEliminar,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Eliminar actividad")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onVolver,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver")
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}