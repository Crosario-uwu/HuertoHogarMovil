package com.example.huertohogarmovil.ui.screens.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.huertohogarmovil.model.User
import com.example.huertohogarmovil.viewmodel.AdminViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminUsuariosScreen(
    navController: NavController,
    vm: AdminViewModel
) {
    val loading by vm.loading.collectAsState()
    val usuarios by vm.usuarios.collectAsState()

    LaunchedEffect(Unit) { vm.cargarUsuarios() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Usuarios") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                    }
                }
            )
        }
    ) { padding ->

        if (loading) {
            Box(Modifier.fillMaxSize(), Alignment.Center) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

        LazyColumn(
            modifier = Modifier.padding(padding).padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(usuarios) { user -> UsuarioCard(user) }
        }
    }
}

@Composable
fun UsuarioCard(user: User) {
    ElevatedCard(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(15.dp)) {
            Text(user.name)
            Text(user.email)
            Text(user.phone ?: "Sin teléfono")
        }
    }
}
