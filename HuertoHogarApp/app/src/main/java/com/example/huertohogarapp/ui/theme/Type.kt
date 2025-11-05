package com.example.huertohogar.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.huertohogarapp.R

// Fuentes personalizadas de Huerto Hogar
// (Debes tener los archivos Montserrat-Regular.ttf y PlayfairDisplay-Bold.ttf en res/font/)
val Montserrat = FontFamily(Font(R.font.montserrat_variablefont_wght))
val PlayfairDisplay = FontFamily(Font(R.font.playfairdisplay_variablefont_wght))

val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = PlayfairDisplay,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        letterSpacing = 0.sp,
        color = GrisOscuro
    ),
    bodyLarge = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = GrisMedio
    ),
    labelLarge = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        letterSpacing = 0.1.sp,
        color = GrisOscuro
    )
)
