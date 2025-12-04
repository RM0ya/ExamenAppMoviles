package com.example.pasteleriakotlin.ui.admins

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GestCategoriasScreen(navController: NavController) {

    val categorias = remember {
        mutableStateListOf<String>().apply {
            addAll(localAdminProducts.map { it.category }.distinct())
        }
    }

    var nuevaCategoria by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestionar Categorías") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF6F0DE)
                )
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = Color(0xFFF6F0DE)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Categorías actuales",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = Color(0xFF6E3F2F),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))


                OutlinedTextField(
                    value = nuevaCategoria,
                    onValueChange = {
                        nuevaCategoria = it
                        errorMessage = null
                    },
                    label = { Text("Nueva categoría") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                if (errorMessage != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = errorMessage ?: "",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        val trimmed = nuevaCategoria.trim()
                        when {
                            trimmed.isEmpty() -> {
                                errorMessage = "La categoría no puede estar vacía"
                            }
                            categorias.any { it.equals(trimmed, ignoreCase = true) } -> {
                                errorMessage = "La categoría ya existe"
                            }
                            else -> {
                                categorias.add(trimmed)
                                nuevaCategoria = ""
                                errorMessage = null
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Text("Agregar categoría")
                }

                Spacer(modifier = Modifier.height(16.dp))


                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    categorias.forEach { categoria ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5EAD3)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = categoria,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color(0xFF6E3F2F)
                                )

                                OutlinedButton(
                                    onClick = { categorias.remove(categoria) },
                                    shape = RoundedCornerShape(50.dp)
                                ) {
                                    Text("Eliminar")
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { navController.navigate("adminHome") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Text("Volver al Panel Administrador")
                }
            }
        }
    }
}
