package com.example.miformacionctma.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun EncabezadoFormacion(
    nombre: String,
    resumen: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Mi Formación CTMA",
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = "Hola, $nombre",
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = resumen,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(
    showBackground = true,
    name = "Tamaño normal"
)
@Composable
fun EncabezadoFormacionPreview() {
    EncabezadoFormacion(
        nombre = "Aprendiz",
        resumen = "Consulta tus actividades formativas."
    )
}

@Preview(
    showBackground = true,
    fontScale = 1.5f,
    name = "Fuente grande"
)
@Composable
fun EncabezadoFormacionFuenteGrandePreview() {
    EncabezadoFormacion(
        nombre = "Aprendiz",
        resumen = "Consulta tus actividades formativas."
    )
}

@Preview(
    showBackground = true,
    widthDp = 600,
    name = "Ancho ampliado"
)
@Composable
fun EncabezadoFormacionAnchoAmpliadoPreview() {
    EncabezadoFormacion(
        nombre = "Aprendiz",
        resumen = "Consulta tus actividades formativas."
    )
}