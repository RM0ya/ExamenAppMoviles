package com.example.pasteleriakotlin.ui.admins

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class AdminOrder(
    val id: Int,
    val productName: String,
    val clientName: String,
    var status: String,
    val price: Double = 0.0
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminPedidosScreen(navController: NavController) {

    val pedidos = remember {
        mutableStateListOf(
            AdminOrder(1, "Cheesecake", "Kevin Vergara", "Pendiente", 15000.0),
            AdminOrder(2, "Brownie", "María López", "En preparación", 8500.0),
            AdminOrder(3, "Torta Vegana", "Juan Pérez", "En reparto", 22000.0),
            AdminOrder(4, "Torta Matrimonial", "Ana Torres", "Entregado", 45000.0)
        )
    }

    val pedidosPendientes = pedidos.count { it.status == "Pendiente" }
    val pedidosEnPreparacion = pedidos.count { it.status == "En preparación" }
    val pedidosEntregados = pedidos.count { it.status == "Entregado" }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestionar Pedidos") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
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
                    .padding(24.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))


                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF5EAD3)),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Resumen de Pedidos",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF6E3F2F),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            StatItem(label = "Pendientes", value = pedidosPendientes.toString(), color = Color(0xFFAD812C))
                            StatItem(label = "En Preparación", value = pedidosEnPreparacion.toString(), color = Color(0xFF6E3F2F))
                            StatItem(label = "Entregados", value = pedidosEntregados.toString(), color = Color(0xFFDAA541))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Listado de Pedidos",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = Color(0xFF6E3F2F),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))


                pedidos.forEach { pedido ->
                    OrderCard(
                        pedido = pedido,
                        onUpdateStatus = { newStatus ->
                            val indice = pedidos.indexOfFirst { it.id == pedido.id }
                            if (indice != -1) {
                                pedidos[indice] = pedidos[indice].copy(status = newStatus)
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))


                OutlinedButton(
                    onClick = { navController.navigate("adminHome") },
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
                        text = "Volver al Panel",
                        color = Color(0xFF6E3F2F),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF5EAD3)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "💼 Gestiona el estado de cada pedido en tiempo real",
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
fun OrderCard(
    pedido: AdminOrder,
    onUpdateStatus: (String) -> Unit
) {
    val statusColor = when (pedido.status) {
        "Pendiente" -> Color(0xFFAD812C)
        "En preparación" -> Color(0xFF6E3F2F)
        "En reparto" -> Color(0xFF8B6F47)
        "Entregado" -> Color(0xFFDAA541)
        else -> Color.Gray
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5EAD3)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Pedido #${pedido.id}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF6E3F2F)
                )

                Card(
                    colors = CardDefaults.cardColors(containerColor = statusColor),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = pedido.status,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            HorizontalDivider(color = Color(0xFFDAA541).copy(alpha = 0.5f))

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Producto:",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                    Text(
                        text = pedido.productName,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF6E3F2F)
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Precio:",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                    Text(
                        text = "$${pedido.price.toInt()}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFAD812C)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Cliente:",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Text(
                text = pedido.clientName,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF6E3F2F)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Actualizar estado:",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6E3F2F)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { onUpdateStatus("En preparación") },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6E3F2F)
                    ),
                    shape = RoundedCornerShape(50),
                    enabled = pedido.status != "Entregado"
                ) {
                    Text(
                        "En Preparación",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold
                    )
                }

                Button(
                    onClick = { onUpdateStatus("Entregado") },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFAD812C)
                    ),
                    shape = RoundedCornerShape(50),
                    enabled = pedido.status != "Entregado"
                ) {
                    Text(
                        "Entregado",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun StatItem(label: String, value: String, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(4.dp)
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF6E3F2F),
            textAlign = TextAlign.Center
        )
    }
}