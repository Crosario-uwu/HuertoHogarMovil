package com.example.huertohogarmovil.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.huertohogarmovil.ui.utils.Validators
import com.example.huertohogarmovil.viewmodel.AuthViewModel
import com.example.huertohogarmovil.viewmodel.PerfilViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarPerfilScreen(
    perfilVM: PerfilViewModel,
    authVM: AuthViewModel,

    ) {
    val usuario by perfilVM.user.collectAsState()
    val loading by perfilVM.loading.collectAsState()
    val error by perfilVM.error.collectAsState()
    val mensaje by perfilVM.mensaje.collectAsState()

    if (usuario == null) {
        Box(Modifier.fillMaxSize(), Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    var nombre by remember { mutableStateOf(usuario!!.name) }
    var telefono by remember { mutableStateOf(usuario!!.phone ?: "") }

    val phoneError = telefono.isNotBlank() && !Validators.isPhoneValid(telefono)

    Scaffold(
        topBar = { TopAppBar(title = { Text("Editar Perfil") }) }
    ) { padding ->

        Column(
            Modifier
                .padding(padding)
                .padding(20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(15.dp))

            OutlinedTextField(
                value = telefono,
                onValueChange = { telefono = it },
                isError = phoneError,
                label = { Text("Teléfono") },
                modifier = Modifier.fillMaxWidth()
            )

            if (phoneError) {
                Text(
                    "Teléfono inválido",
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(Modifier.height(25.dp))

            Button(
                enabled = !loading && !phoneError && nombre.isNotBlank(),
                onClick = {
                    perfilVM.actualizarPerfil(
                        nombre = nombre,
                        telefono = telefono.ifBlank { null }
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar cambios")
            }

            if (mensaje != null) {
                Spacer(Modifier.height(10.dp))
                Text(mensaje!!, color = MaterialTheme.colorScheme.primary)
            }

            if (error != null) {
                Spacer(Modifier.height(10.dp))
                Text(error!!, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
