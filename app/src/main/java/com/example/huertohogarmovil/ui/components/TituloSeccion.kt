package com.example.huertohogarmovil.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.example.huertohogarmovil.ui.theme.MarronClaro

@Composable
fun TituloSeccion(text: String) {
    Text(
        text = text,
        fontSize = 22.sp,
        color = MarronClaro
    )
}
