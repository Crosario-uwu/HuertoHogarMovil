package com.example.huertohogarapp.ui.screen

package com.example.huertohogarapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.huertohogarapp.ui.components.FormTextField
import com.example.huertohogarapp.ui.components.PrimaryButton
import com.example.huertohogarapp.ui.theme.HuertoHogarTheme
import com.example.huertohogarapp.viewmodel.FormularioViewModel
import kotlinx.coroutines.launch

@Composable
fun FormularioScreen(
    viewModel: FormularioViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Preferencias Huerto Hogar",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Cuéntanos un poco de ti para recomendarte productos frescos.",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.height(16.dp))

        // NOMBRE
        FormTextField(
            value = uiState.nombre,
            onValueChange = { viewModel.actualizarNombre(it) },
            label = "Nombre",
            error = uiState.errores.nombre
        )
        Spacer(Modifier.height(8.dp))

        // CORREO
        FormTextField(
            value = uiState.correo,
            onValueChange = { viewModel.actualizarCorreo(it) },
            label = "Correo electrónico",
            error = uiState.errores.correo
        )
        Spacer(Modifier.height(8.dp))

        // CATEGORÍA FAVORITA (Frutas, Verduras, Orgánicos, Lácteos)
        Text(
            text = "Categoría favorita",
            style = MaterialTheme.typography.labelLarge
        )
        Spacer(Modifier.height(4.dp))

        val categorias = listOf("Frutas", "Verduras", "Orgánicos", "Lácteos")
        Row {
            categorias.forEach { categoria ->
                androidx.compose.material3.AssistChip(
                    onClick = { viewModel.actualizarCategoriaFavorita(categoria) },
                    label = { Text(categoria) },
                    modifier = Modifier.padding(end = 4.dp),
                    colors = androidx.compose.material3.AssistChipDefaults.assistChipColors(
                        containerColor =
                            if (uiState.categoriaFavorita == categoria)
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                            else
                                MaterialTheme.colorScheme.surface
                    )
                )
            }
        }

        if (uiState.errores.categoriaFavorita != null) {
            Spacer(Modifier.height(4.dp))
            Text(
                text = uiState.errores.categoriaFavorita!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall
            )
        }

        Spacer(Modifier.height(16.dp))

        // VOLUMEN (nivel de preferencia por productos orgánicos, por ejemplo)
        Text("Nivel de preferencia por productos orgánicos:")
        Spacer(Modifier.height(4.dp))
        androidx.compose.material3.Slider(
            value = uiState.volumen,
            onValueChange = { viewModel.actualizarVolumen(it) },
            valueRange = 0f..1f,
            steps = 3 // 4 posiciones
        )
        Text(
            text = when {
                uiState.volumen < 0.25f -> "Bajo"
                uiState.volumen < 0.5f -> "Medio"
                uiState.volumen < 0.75f -> "Alto"
                else -> "Muy alto"
            },
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(Modifier.height(16.dp))

        // ACEPTA TÉRMINOS
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = uiState.aceptaTerminos,
                onCheckedChange = { viewModel.cambiarAceptaTerminos(it) }
            )
            Text(
                text = "Acepto recibir novedades y ofertas de Huerto Hogar",
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(Modifier.height(24.dp))

        PrimaryButton(
            text = "Guardar preferencias",
            onClick = {
                val exito = viewModel.enviarFormulario()
                if (exito) {
                    scope.launch {
                        val result = snackbarHostState.showSnackbar(
                            message = "Preferencias guardadas correctamente 🌿",
                            actionLabel = "OK"
                        )
                        if (result == SnackbarResult.ActionPerformed) {
                            // Nada especial, solo cerrar snackbar
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        SnackbarHost(hostState = snackbarHostState)
    }
}

@Preview(showBackground = true)
@Composable
fun FormularioScreenPreview() {
    HuertoHogarTheme {
        // En el preview no necesitamos un VM real, pero usamos el por defecto
        FormularioScreen()
    }
}
