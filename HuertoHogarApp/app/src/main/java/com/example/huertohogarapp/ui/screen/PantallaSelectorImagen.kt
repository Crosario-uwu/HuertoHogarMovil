package com.example.huertohogarapp.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.huertohogarapp.ui.theme.HuertoHogarTheme
import com.example.huertohogarapp.viewmodel.SelectorImagenViewModel

@Composable
fun PantallaSelectorImagen(
    viewModel: SelectorImagenViewModel = viewModel()
) {
    val imagenUriString by viewModel.imagenUri.collectAsState()
    val imagenUri: Uri? = imagenUriString?.let { Uri.parse(it) }

    val lanzadorGaleria = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uriSeleccionada ->
        viewModel.actualizarImagen(uriSeleccionada?.toString())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Selecciona una foto para tu perfil o reseña",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .size(220.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            if (imagenUri != null) {
                AsyncImage(
                    model = imagenUri,
                    contentDescription = "Imagen seleccionada",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp))
                )
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Sin imagen seleccionada",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        Button(onClick = { lanzadorGaleria.launch("image/*") }) {
            Text("Elegir desde galería")
        }

        Spacer(Modifier.height(8.dp))

        if (imagenUri != null) {
            Button(onClick = { viewModel.actualizarImagen(null) }) {
                Text("Quitar imagen")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaSelectorImagenPreview() {
    HuertoHogarTheme {
        // Preview con ViewModel por defecto (sin imagen)
        PantallaSelectorImagen()
    }
}
package com.example.huertohogarapp.ui.screen

