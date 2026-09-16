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

    var intentoGuardar by rememberSaveable {
        mutableStateOf(false)
    }

    val errorTitulo =
        if (intentoGuardar) validarTitulo(titulo) else null

    val errorDescripcion =
        if (intentoGuardar) validarDescripcion(descripcion) else null

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
            value = titulo,
            onValueChange = { nuevoTitulo ->
                titulo = nuevoTitulo
            },
            label = {
                Text("Título")
            },
            modifier = Modifier.fillMaxWidth(),
            isError = errorTitulo != null,
            supportingText = {
                if (errorTitulo != null) {
                    Text(errorTitulo)
                } else {
                    Text("${titulo.length}/80")
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = descripcion,
            onValueChange = { nuevaDescripcion ->
                descripcion = nuevaDescripcion
            },
            label = {
                Text("Descripción")
            },
            modifier = Modifier.fillMaxWidth(),
            isError = errorDescripcion != null,
            supportingText = {
                if (errorDescripcion != null) {
                    Text(errorDescripcion)
                } else {
                    Text("${descripcion.length}/240")
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
                intentoGuardar = true

                val tituloError = validarTitulo(titulo)
                val descripcionError = validarDescripcion(descripcion)

                if (tituloError == null && descripcionError == null) {
                    onGuardar(
                        titulo.trim(),
                        descripcion.trim()
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar")
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