package com.example.pasteleriakotlin.ui.admins

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pasteleriakotlin.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminHomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Panel Administrador") },
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
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))


                Box(
                    modifier = Modifier
                        .background(Color(0xFFF5EAD3), shape = RoundedCornerShape(20.dp))
                        .padding(32.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo1),
                        contentDescription = "Logo Pastelería",
                        modifier = Modifier.size(100.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))


                Text(
                    text = "Bienvenido, Administrador",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color(0xFFAD812C),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Gestiona la pastelería desde aquí",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF6E3F2F),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))


                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF5EAD3)),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        StatCard(title = "Productos", value = "24", color = Color(0xFFAD812C))
                        StatCard(title = "Pedidos", value = "18", color = Color(0xFF6E3F2F))
                        StatCard(title = "Usuarios", value = "156", color = Color(0xFFDAA541))
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))


                Text(
                    text = "Opciones de Gestión",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFF6E3F2F),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))


                AdminMenuButton(
                    text = "Gestionar Productos",
                    backgroundColor = Color(0xFF6E3F2F),
                    onClick = {
                        navController.navigate("admin/products")
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                AdminMenuButton(
                    text = "Ver Pedidos",
                    backgroundColor = Color(0xFFAD812C),
                    onClick = {
                         navController.navigate("admin/orders")
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))


                AdminMenuButton(
                    text = "Gestionar Categorías",
                    backgroundColor = Color(0xFF8B6F47),
                    onClick = {
                         navController.navigate("admin/categories")
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))


                Spacer(modifier = Modifier.height(24.dp))

                HorizontalDivider(
                    color = Color(0xFF6E3F2F).copy(alpha = 0.3f),
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))


                OutlinedButton(
                    onClick = { navController.navigate("products") },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFF6E3F2F)
                    ),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    border = ButtonDefaults.outlinedButtonBorder.copy(width = 2.dp)
                ) {
                    Text(
                        text = "Ver Catálogo Público",
                        color = Color(0xFF6E3F2F),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))


                OutlinedButton(
                    onClick = {
                        navController.navigate("inicio") {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFFAD812C)
                    ),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    border = ButtonDefaults.outlinedButtonBorder.copy(width = 2.dp)
                ) {
                    Text(
                        text = "Cerrar Sesión",
                        color = Color(0xFFAD812C),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))


                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF5EAD3)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Acceso exclusivo para administradores del sistema",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF6E3F2F),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun AdminMenuButton(
    text: String,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(50),
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
    ) {
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun StatCard(title: String, value: String, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF6E3F2F)
        )
    }
}