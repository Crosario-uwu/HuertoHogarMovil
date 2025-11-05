package com.example.huertohogarapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = VerdeEsmeralda,
    secondary = AmarilloMostaza,
    tertiary = MarronClaro,
    background = BlancoSuave,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = GrisOscuro,
    onBackground = GrisOscuro,
    onSurface = GrisOscuro
)

@Composable
fun HuertoHogarTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
