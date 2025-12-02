package com.example.huertohogarmovil.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ColorScheme = lightColorScheme(
    primary = VerdeEsmeralda,
    secondary = AmarilloMostaza,
    tertiary = MarronClaro,
    background = FondoSuave,
    onPrimary = Color.White,
    onBackground = GrisOscuro
)

@Composable
fun HuertoHogarTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ColorScheme,
        typography = Typography,
        content = content
    )
}
