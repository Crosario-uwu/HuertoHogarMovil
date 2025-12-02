package com.example.huertohogarmovil.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.huertohogarmovil.ui.navigation.AppScreen
import com.example.huertohogarmovil.viewmodel.AuthViewModel
import com.example.huertohogarmovil.model.User
import com.example.huertohogarmovil.ui.utils.Validators

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    authVM: AuthViewModel
) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }

    val loading by authVM.loading.collectAsState()
    val error by authVM.error.collectAsState()

    val emailError = email.isNotBlank() && !Validators.isEmailValid(email)
    val passError = password.isNotBlank() && !Validators.isPasswordValid(password)
    val confirmError = confirm.isNotBlank() && confirm != password
    val phoneError = phone.isNotBlank() && !Validators.isPhoneValid(phone)

    val canRegister =
        name.isNotBlank() &&
                email.isNotBlank() &&
                !emailError &&
                !passError &&
                !confirmError &&
                !phoneError &&
                password.isNotBlank()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crear Cuenta") },
                navigationIcon = {
                    IconButton(onClick = {
                        authVM.setError(null)
                        navController.popBackStack()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // NOMBRE
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                    authVM.setError(null)    // ✔ limpiar
                },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            // EMAIL
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    authVM.setError(null)    // ✔ limpiar
                },
                label = { Text("Correo electrónico") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )
            if (emailError) Text("Correo inválido", color = MaterialTheme.colorScheme.error)

            Spacer(Modifier.height(12.dp))

            // TELÉFONO
            OutlinedTextField(
                value = phone,
                onValueChange = {
                    phone = it
                    authVM.setError(null)
                },
                label = { Text("Teléfono (opcional)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )
            if (phoneError) Text("Teléfono inválido", color = MaterialTheme.colorScheme.error)

            Spacer(Modifier.height(12.dp))

            // PASSWORD
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    authVM.setError(null)
                },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            // CONFIRM PASSWORD
            OutlinedTextField(
                value = confirm,
                onValueChange = {
                    confirm = it
                    authVM.setError(null)
                },
                label = { Text("Confirmar contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            if (confirmError) Text("Las contraseñas no coinciden", color = MaterialTheme.colorScheme.error)

            Spacer(Modifier.height(20.dp))

            // BOTÓN CREAR CUENTA
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = canRegister && !loading,
                onClick = {

                    authVM.setError(null)

                    val nuevo = User(
                        id = 0,
                        name = name,
                        email = email,
                        phone = phone.ifBlank { null },
                        password = password
                    )

                    authVM.register(nuevo) { success ->
                        if (success) {
                            authVM.setError(null)
                            navController.navigate(AppScreen.Login.route) {
                                popUpTo(AppScreen.Register.route) { inclusive = true }
                            }
                        }
                    }
                }
            ) {
                Text("Crear cuenta")
            }

            if (error != null) {
                Spacer(Modifier.height(15.dp))
                Text(error!!, color = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.height(20.dp))

            // YA TENGO CUENTA
            TextButton(onClick = {
                authVM.setError(null)
                navController.popBackStack()
            }) {
                Text("Ya tengo cuenta")
            }
        }
    }
}
