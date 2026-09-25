package com.example.miformacionctma.util

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun recordarSelectorDeImagen(
    onImagenSeleccionada: (Uri) -> Unit,
    onCancelado: () -> Unit
): () -> Unit {

    var lanzarSelector by remember {
        mutableStateOf(false)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->

        if (uri != null) {
            onImagenSeleccionada(uri)
        } else {
            onCancelado()
        }
    }

    LaunchedEffect(lanzarSelector) {
        if (lanzarSelector) {
            lanzarSelector = false

            launcher.launch(
                PickVisualMediaRequest(
                    ActivityResultContracts.PickVisualMedia.ImageOnly
                )
            )
        }
    }

    return {
        lanzarSelector = true
    }
}