package com.example.miformacionctma.ui.screens

import androidx.compose.foundation.layout.Arrangement
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
import com.example.miformacionctma.domain.validarDescripcion
import com.example.miformacionctma.domain.validarTitulo

@Composable
fun FormularioActividadScreen(
    onGuardar: (String, String) -> Unit,
    onCancelar: () -> Unit
) {
    var titulo by remember {
        mutableStateOf("")
    }

    var descripcion by remember {
        mutableStateOf("")
    }

    var intentoGuardar by remember {
        mutableStateOf(false)
    }

    // Evita que Guardar se ejecute varias veces
    var guardando by remember {
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Crear actividad"
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        OutlinedTextField(
            value = titulo,
            onValueChange = {
                if (!guardando) {
                    titulo = it
                }
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
            singleLine = true,
            enabled = !guardando
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = descripcion,
            onValueChange = {
                if (!guardando) {
                    descripcion = it
                }
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
            },
            enabled = !guardando
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Button(
            onClick = {

                // Si ya está guardando, no hace nada
                if (guardando) {
                    return@Button
                }

                intentoGuardar = true

                val tituloValido =
                    validarTitulo(titulo) == null

                val descripcionValida =
                    validarDescripcion(descripcion) == null

                if (
                    tituloValido &&
                    descripcionValida
                ) {

                    // Bloqueamos inmediatamente el botón
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
                if (guardando) {
                    "Guardando..."
                } else {
                    "Guardar"
                }
            )
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Button(
            onClick = onCancelar,
            modifier = Modifier.fillMaxWidth(),
            enabled = !guardando
        ) {
            Text("Cancelar")
        }
    }
}