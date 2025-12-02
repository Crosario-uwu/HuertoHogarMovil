package com.example.huertohogarmovil.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ErrorMessage(message: String?) {
    if (message != null) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error
        )
    }
}
