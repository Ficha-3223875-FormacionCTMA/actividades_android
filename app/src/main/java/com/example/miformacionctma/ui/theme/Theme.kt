package com.example.miformacionctma.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = AzulPrincipal,
    secondary = Verde,
    tertiary = Naranja,
    background = FondoSuave
)
private val DarkColors = darkColorScheme(
    primary = Color(0xFFB8C9FF),
    secondary = Color(0xFFA6D8BC),
    tertiary = Color(0xFFFFB870)
)

@Composable
fun MiFormacionCTMATheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val scheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColors
        else -> LightColors
    }
    MaterialTheme(colorScheme = scheme, typography = Typography(), content = content)
}
