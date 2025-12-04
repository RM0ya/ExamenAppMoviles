package com.example.pasteleriakotlin.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pasteleriakotlin.data.localdao.sampleProducts
import com.example.pasteleriakotlin.viewmodel.CarritoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: Int,
    navController: NavController,
    carritoViewModel: CarritoViewModel
) {
    val product = sampleProducts.find { it.id == productId }
    var quantity by remember { mutableStateOf(1) }
    var showSnackbar by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }

    val totalItemsInCart = carritoViewModel.getTotalItems()

    if (product != null) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Detalle del Producto") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                        }
                    },
                    actions = {
                        BadgedBox(
                            badge = {
                                if (totalItemsInCart > 0) {
                                    Badge {
                                        Text(totalItemsInCart.toString())
                                    }
                                }
                            },
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            IconButton(onClick = { navController.navigate("carrito") }) {
                                Icon(
                                    Icons.Filled.ShoppingCart,
                                    contentDescription = "Carrito",
                                    tint = Color(0xFFAD812C)
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFFF6F0DE)
                    )
                )
            },
            snackbarHost = {
                if (showSnackbar) {
                    Snackbar(
                        modifier = Modifier.padding(16.dp),
                        containerColor = Color(0xFF6E3F2F),
                        action = {
                            TextButton(onClick = { showSnackbar = false }) {
                                Text("OK", color = Color.White)
                            }
                        }
                    ) {
                        Text(snackbarMessage, color = Color.White)
                    }
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF5EAD3)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Image(
                        painter = painterResource(id = product.imageRes),
                        contentDescription = product.nombre,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(280.dp),
                        contentScale = ContentScale.Crop
                    )
                }


                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF5EAD3)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            product.nombre,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF6E3F2F)
                        )

                        Spacer(Modifier.height(8.dp))

                        Text(
                            product.descripcion,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Gray
                        )

                        Spacer(Modifier.height(12.dp))

                        Text(
                            "Precio: $${product.precio}",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFAD812C)
                        )

                        Spacer(Modifier.height(16.dp))

                        HorizontalDivider(color = Color(0xFFDAA541))

                        Spacer(Modifier.height(16.dp))


                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                "Cantidad: ",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleMedium,
                                color = Color(0xFF6E3F2F)
                            )
                            Spacer(Modifier.weight(1f))

                            Button(
                                onClick = { if (quantity > 1) quantity-- },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF6E3F2F)
                                ),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.size(40.dp),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Text("-", color = Color.White, style = MaterialTheme.typography.titleLarge)
                            }

                            Text(
                                "$quantity",
                                modifier = Modifier.padding(horizontal = 24.dp),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF6E3F2F)
                            )

                            Button(
                                onClick = { quantity++ },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF6E3F2F)
                                ),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.size(40.dp),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Text("+", color = Color.White, style = MaterialTheme.typography.titleLarge)
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))


                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Button(
                        onClick = {
                            repeat(quantity) {
                                carritoViewModel.addToCart(product)
                            }
                            snackbarMessage = "$quantity ${product.nombre} agregado(s) al carrito"
                            showSnackbar = true
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFAD812C)
                        ),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Icon(
                            Icons.Filled.ShoppingCart,
                            contentDescription = null,
                            tint = Color.White
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "Agregar al Carrito",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }


                    Button(
                        onClick = {
                            repeat(quantity) {
                                carritoViewModel.addToCart(product)
                            }
                            navController.navigate("carrito")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6E3F2F)
                        ),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(
                            "Comprar Ahora",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }


                    OutlinedButton(
                        onClick = { navController.popBackStack() },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFF6E3F2F)
                        ),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = null,
                            tint = Color(0xFF6E3F2F)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "Volver a Productos",
                            color = Color(0xFF6E3F2F),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))
            }
        }


        LaunchedEffect(showSnackbar) {
            if (showSnackbar) {
                kotlinx.coroutines.delay(2000)
                showSnackbar = false
            }
        }
    } else {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "Producto no encontrado",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFF6E3F2F)
                )
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = { navController.popBackStack() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFAD812C)
                    )
                ) {
                    Text("Volver", color = Color.White)
                }
            }
        }
    }
}