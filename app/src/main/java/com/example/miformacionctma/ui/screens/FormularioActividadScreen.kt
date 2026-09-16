package com.example.miformacionctma.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.miformacionctma.domain.FormularioActividadUiState
import com.example.miformacionctma.domain.validarDescripcion
import com.example.miformacionctma.domain.validarTitulo

@Composable
fun FormularioActividadScreen(
    onGuardar: (String, String) -> Unit,
    onCancelar: () -> Unit
) {

    var titulo by rememberSaveable {
        mutableStateOf("")
    }

    var descripcion by rememberSaveable {
        mutableStateOf("")
    }

    var errorTitulo by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    var errorDescripcion by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    var intentoGuardar by rememberSaveable {
        mutableStateOf(false)
    }

    var guardando by rememberSaveable {
        mutableStateOf(false)
    }

    val estado = FormularioActividadUiState(
        titulo = titulo,
        descripcion = descripcion,
        errorTitulo = errorTitulo,
        errorDescripcion = errorDescripcion,
        intentoGuardar = intentoGuardar
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {

        Text(
            text = "Crear actividad",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = estado.titulo,
            onValueChange = { nuevoTitulo ->
                titulo = nuevoTitulo

                if (intentoGuardar) {
                    errorTitulo = validarTitulo(nuevoTitulo)
                }
            },
            label = {
                Text("Título")
            },
            modifier = Modifier.fillMaxWidth(),
            isError = estado.errorTitulo != null,
            supportingText = {
                if (estado.errorTitulo != null) {
                    Text(estado.errorTitulo!!)
                } else {
                    Text("${estado.titulo.length}/80")
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = estado.descripcion,
            onValueChange = { nuevaDescripcion ->
                descripcion = nuevaDescripcion

                if (intentoGuardar) {
                    errorDescripcion = validarDescripcion(nuevaDescripcion)
                }
            },
            label = {
                Text("Descripción")
            },
            modifier = Modifier.fillMaxWidth(),
            isError = estado.errorDescripcion != null,
            supportingText = {
                if (estado.errorDescripcion != null) {
                    Text(estado.errorDescripcion!!)
                } else {
                    Text("${estado.descripcion.length}/240")
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            minLines = 4
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (guardando) return@Button

                val nuevoErrorTitulo = validarTitulo(titulo)
                val nuevoErrorDescripcion = validarDescripcion(descripcion)

                errorTitulo = nuevoErrorTitulo
                errorDescripcion = nuevoErrorDescripcion
                intentoGuardar = true

                if (nuevoErrorTitulo == null &&
                    nuevoErrorDescripcion == null &&
                    estado.puedeGuardar
                ) {
                    guardando = true

                    onGuardar(
                        titulo.trim(),
                        descripcion.trim()
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !guardando
        ) {
            Text(
                text = if (guardando) {
                    "Guardando..."
                } else {
                    "Guardar"
                }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onCancelar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancelar")
        }
    }
}