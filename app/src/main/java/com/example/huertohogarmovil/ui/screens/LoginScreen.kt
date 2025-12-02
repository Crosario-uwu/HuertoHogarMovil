package com.example.huertohogarmovil.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.huertohogarmovil.ui.navigation.AppScreen
import com.example.huertohogarmovil.ui.utils.Validators
import com.example.huertohogarmovil.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    authVM: AuthViewModel
) {

    val loading by authVM.loading.collectAsState()
    val error by authVM.error.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val emailError = email.isNotBlank() && !Validators.isEmailValid(email)
    val canLogin = email.isNotBlank() && password.isNotBlank() && !emailError

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Iniciar Sesión") },
                navigationIcon = {

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

            Spacer(Modifier.height(30.dp))

            //------------------------------------
            //  EMAIL
            //------------------------------------
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    authVM.setError(null)   // ← ESTE SÍ EXISTE
                },
                label = { Text("Correo electrónico") },
                isError = emailError,
                modifier = Modifier.fillMaxWidth()
            )

            if (emailError)
                Text("Correo inválido", color = MaterialTheme.colorScheme.error)

            Spacer(Modifier.height(15.dp))

            //------------------------------------
            //  PASSWORD
            //------------------------------------
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    authVM.setError(null)  // ← LIMPIA ERROR
                },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(20.dp))

            //------------------------------------
            //  LOGIN BUTTON
            //------------------------------------
            Button(
                onClick = {
                    authVM.setError(null)

                    if (!Validators.isEmailValid(email)) {
                        authVM.setError("Ingrese un correo válido")
                        return@Button
                    }

                    if (password.isBlank()) {
                        authVM.setError("Debe ingresar su contraseña")
                        return@Button
                    }

                    authVM.login(email, password) { user ->
                        if (user != null) {

                            // REGLA: ADMIN = correo duoc
                            val esAdmin = email.trim().lowercase().endsWith("@duocuc.cl")

                            if (esAdmin) {
                                navController.navigate(AppScreen.HomeAdmin.route) {
                                    popUpTo(AppScreen.Login.route) { inclusive = true }
                                }
                            } else {
                                navController.navigate(AppScreen.Home.route) {
                                    popUpTo(AppScreen.Login.route) { inclusive = true }
                                }
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !loading && canLogin
            ) {
                if (loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Ingresar")
                }
            }

            //------------------------------------
            //  ERROR MESSAGE
            //------------------------------------
            if (error != null) {
                Spacer(Modifier.height(20.dp))
                Text(
                    text = error ?: "",
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(Modifier.height(25.dp))

            //------------------------------------
            //  GO TO REGISTER
            //------------------------------------
            TextButton(onClick = {
                authVM.setError(null)
                navController.navigate(AppScreen.Register.route)
            }) {
                Text("Crear una cuenta")
            }
        }
    }
}
