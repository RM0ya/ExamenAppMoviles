package com.example.pasteleriakotlin.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pasteleriakotlin.viewmodel.ApiViewModel

@Composable
fun ApiTestScreen(
    apiViewModel: ApiViewModel = viewModel()
) {
    val postExterno by apiViewModel.postExterno.collectAsState()
    val loginResponse by apiViewModel.loginResponse.collectAsState()
    val error by apiViewModel.error.collectAsState()

    val nombreState = remember { mutableStateOf("") }
    val contrasenaState = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {


        Button(onClick = { apiViewModel.cargarPostExterno() }) {
            Text("Cargar post externo")
        }

        Spacer(modifier = Modifier.height(8.dp))

        postExterno?.let {
            Text(text = "Titulo API externa: ${it.title}")
        }

        Spacer(modifier = Modifier.height(24.dp))


        OutlinedTextField(
            value = nombreState.value,
            onValueChange = { nombreState.value = it },
            label = { Text("Nombre usuario") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = contrasenaState.value,
            onValueChange = { contrasenaState.value = it },
            label = { Text("Contraseña") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            apiViewModel.loginBackend(
                nombre = nombreState.value,
                contrasena = contrasenaState.value
            )
        }) {
            Text("Login backend")
        }

        Spacer(modifier = Modifier.height(8.dp))

        loginResponse?.let {
            Text(text = "Login OK, usuario: ${it.usuario.nombre}")
        }

        error?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it)
        }
    }
}
