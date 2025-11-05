package com.example.huertohogarapp.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.huertohogarapp.model.Usuario
import com.example.huertohogarapp.ui.theme.HuertoHogarTheme
import com.example.huertohogarapp.viewmodel.UsuarioViewModel

@Composable
fun PerfilScreen(
    usuarioViewModel: UsuarioViewModel,
    onLogout: () -> Unit
) {
    val usuario by usuarioViewModel.usuario.collectAsState()

    var fotoUri by remember { mutableStateOf<Uri?>(null) }

    val lanzadorGaleria = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        fotoUri = uri
        usuarioViewModel.actualizarFotoPerfil(uri?.toString())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Mi perfil", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))

        val uriFinal = fotoUri ?: usuario?.fotoPerfilUri?.let { Uri.parse(it) }

        if (uriFinal != null) {
            AsyncImage(
                model = uriFinal,
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
            )
        } else {
            Text("Sin foto de perfil")
        }

        Spacer(Modifier.height(8.dp))

        Button(onClick = { lanzadorGaleria.launch("image/*") }) {
            Text("Cambiar foto")
        }

        Spacer(Modifier.height(24.dp))

        Text("Nombre: ${usuario?.nombre ?: "-"}")
        Text("Correo: ${usuario?.correo ?: "-"}")
        Text("Teléfono: ${usuario?.telefono ?: "-"}")

        Spacer(Modifier.height(24.dp))

        Button(onClick = onLogout, colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.error
        )) {
            Text("Cerrar sesión")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PerfilScreenPreview() {
    // Preview con usuario "fake"
    val fakeVm = object : UsuarioViewModel() {}
    HuertoHogarTheme {
        PerfilScreen(
            usuarioViewModel = fakeVm,
            onLogout = {}
        )
    }
}
