package com.example.huertohogar.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color

// 🌿 Paleta de colores Huerto Hogar
private val DarkColorScheme = darkColorScheme(
    primary = VerdeEsmeralda,
    secondary = AmarilloMostaza,
    tertiary = MarronClaro,
    background = Color(0xFF1B1B1B),
    surface = Color(0xFF121212),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.White,
    onBackground = BlancoSuave,
    onSurface = BlancoSuave
)

private val LightColorScheme = lightColorScheme(
    primary = VerdeEsmeralda,
    secondary = AmarilloMostaza,
    tertiary = MarronClaro,
    background = BlancoSuave,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = GrisOscuro,
    onTertiary = BlancoSuave,
    onBackground = GrisOscuro,
    onSurface = GrisOscuro
)

@Composable
fun HuertoHogarTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
