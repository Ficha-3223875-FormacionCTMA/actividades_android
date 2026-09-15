package com.example.miformacionctma.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ColoresCTMA = lightColorScheme(
    primary = Color(0xFF386641),
    secondary = Color(0xFF6A994E),
    tertiary = Color(0xFFA7C957)
)

@Composable
fun MiFormacionCTMATheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = ColoresCTMA,
        typography = Typography,
        content = content
    )
}