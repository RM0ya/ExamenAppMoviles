package com.example.pasteleriakotlin.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pasteleriakotlin.data.ViewModelsApi.BackendLoginViewModel

@Composable
fun BackendLoginScreen(
    backendLoginViewModel: BackendLoginViewModel = viewModel()
) {
    val nombreState = remember { mutableStateOf("") }
    val contrasenaState = remember { mutableStateOf("") }

    val loginResponse by backendLoginViewModel.loginResponse.collectAsState()
    val loading by backendLoginViewModel.loading.collectAsState()
    val error by backendLoginViewModel.error.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        OutlinedTextField(
            value = nombreState.value,
            onValueChange = { nombreState.value = it },
            label = { Text("Nombre de usuario") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = contrasenaState.value,
            onValueChange = { contrasenaState.value = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                backendLoginViewModel.login(
                    nombre = nombreState.value,
                    contrasena = contrasenaState.value
                )
            }
        ) {
            Text("Login Spring Boot")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (loading) {
            CircularProgressIndicator()
        }

        error?.let {
            Text(text = it, color = Color.Red)
            Spacer(modifier = Modifier.height(8.dp))
        }

        loginResponse?.let { resp ->
            Text("Login OK")
            Text("Token: ${resp.token}")
            Text("Usuario: ${resp.usuario.nombre}")
            Text("Rol: ${resp.usuario.rol ?: "sin rol"}")
        }
    }
}