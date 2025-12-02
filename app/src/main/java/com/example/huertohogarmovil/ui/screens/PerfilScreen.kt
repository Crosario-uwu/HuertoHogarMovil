package com.example.huertohogarmovil.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.huertohogarmovil.ui.components.CampoTexto
import com.example.huertohogarmovil.ui.components.ImagenInteligente
import com.example.huertohogarmovil.ui.components.PrimaryButton
import com.example.huertohogarmovil.viewmodel.PerfilViewModel
import com.example.huertohogarmovil.ui.utils.createImageUri
import com.example.huertohogarmovil.viewmodel.AuthViewModel


@Composable
fun PerfilScreen(
    navController: NavHostController,
    viewModel: PerfilViewModel,
    authVM: AuthViewModel
) {
    val context = LocalContext.current

    val usuario by viewModel.user.collectAsState()
    val uriImagen by viewModel.uriImagen.collectAsState()
    val historial by viewModel.historial.collectAsState()

    val cargando by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()
    val mensaje by viewModel.mensaje.collectAsState()

    // Campos editables
    var nombre by remember(usuario) { mutableStateOf(usuario?.name ?: "") }
    var telefono by remember(usuario) { mutableStateOf(usuario?.phone ?: "") }

    // ============= GALERÍA =============
    val launcherGaleria = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        viewModel.actualizarImagenGaleria(uri?.toString())
    }

    // ============= CÁMARA (FileProvider) =============
    var tempUri by remember { mutableStateOf<Uri?>(null) }

    val launcherCamara = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { ok ->
        if (ok && tempUri != null) {
            viewModel.actualizarImagenCamara(tempUri.toString())
        }
    }

    // ============= UI =============
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // FOTO DEL PERFIL
        ImagenInteligente(
            uri = uriImagen,
            size = 140
        )

        Spacer(Modifier.height(16.dp))

        // DATOS DE USUARIO (reales)
        if (usuario != null) {
            Text(usuario!!.email, style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(Modifier.height(16.dp))

        // FORMULARIO
        CampoTexto(
            valor = nombre,
            onValueChange = { nombre = it },
            etiqueta = "Nombre"
        )
        Spacer(Modifier.height(12.dp))

        CampoTexto(
            valor = telefono ?: "",
            onValueChange = { telefono = it },
            etiqueta = "Teléfono"
        )

        Spacer(Modifier.height(20.dp))

        PrimaryButton(
            text = "Seleccionar desde galería",
            onClick = { launcherGaleria.launch("image/*") }
        )

        Spacer(Modifier.height(12.dp))

        PrimaryButton(
            text = "Tomar foto con cámara",
            onClick = {
                val uri = createImageUri(context)
                tempUri = uri
                launcherCamara.launch(uri)
            }
        )

        Spacer(Modifier.height(20.dp))

        PrimaryButton(
            text = if (cargando) "Guardando..." else "Guardar cambios",
            isLoading = cargando,
            onClick = {
                viewModel.actualizarPerfil(
                    nombre = nombre,
                    telefono = telefono.ifBlank { null }
                )
            }
        )

        Spacer(Modifier.height(20.dp))

        // MENSAJES
        error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        mensaje?.let { Text(it, color = MaterialTheme.colorScheme.primary) }

        Spacer(Modifier.height(20.dp))

        // ============= HISTORIAL DE PEDIDOS =============
        Text("Historial de pedidos", style = MaterialTheme.typography.titleMedium)

        if (historial.isEmpty()) {
            Text("No tienes pedidos aún.")
        } else {
            historial.forEach { orden ->
                Text("Orden #${orden.id} - ${orden.status}")
                Text("Fecha: ${orden.createdAt}")
                Spacer(Modifier.height(10.dp))
            }
        }
    }
}
