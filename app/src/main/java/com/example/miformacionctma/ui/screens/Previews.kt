package com.example.miformacionctma.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.miformacionctma.model.*
import com.example.miformacionctma.ui.components.TarjetaActividad
import com.example.miformacionctma.ui.theme.MiFormacionCTMATheme

private val previewActividad = ActividadFormativa(99, "Actividad con un título largo para comprobar que la interfaz no recorta el contenido", "Texto de ejemplo para la revisión visual.", 100, 2, Prioridad.ALTA, "2099-12-15")

@Preview(showBackground = true, name = "Tarjeta normal")
@Composable fun PreviewTarjetaNormal() { MiFormacionCTMATheme { TarjetaActividad(previewActividad, {}) } }

@Preview(showBackground = true, fontScale = 1.5f, name = "Fuente grande")
@Composable fun PreviewFuenteGrande() { MiFormacionCTMATheme { TarjetaActividad(previewActividad.copy(progreso = 0), {}) } }
