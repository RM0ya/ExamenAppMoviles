package com.example.pasteleriakotlin.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pasteleriakotlin.data.localdao.sampleProducts
import com.example.pasteleriakotlin.model.Product
import com.example.pasteleriakotlin.viewmodel.CarritoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(navController: NavController, carritoViewModel: CarritoViewModel) {
    var selectedCategory by remember { mutableStateOf("all") }
    var showSnackbar by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }

    val cartItems by carritoViewModel.cartItems.collectAsState()
    val categories = listOf("all") + sampleProducts.map { it.categoria }.distinct()

    val filteredProducts = if (selectedCategory == "all") {
        sampleProducts
    } else {
        sampleProducts.filter { it.categoria == selectedCategory }
    }

    val totalItemsInCart = carritoViewModel.getTotalItems()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nuestros Productos") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("inicio") }) {
                        Icon(Icons.Filled.Home, contentDescription = "Volver a Inicio")
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
                .padding(16.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Categoría: ", fontWeight = FontWeight.Bold)
                Spacer(Modifier.width(8.dp))
                DropdownMenuCategory(
                    categories = categories,
                    selected = selectedCategory,
                    onSelect = { selectedCategory = it }
                )
            }

            Spacer(Modifier.height(16.dp))


            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(filteredProducts) { product ->
                    ProductCard(
                        product = product,
                        onProductClick = {
                            navController.navigate("productDetail/${product.id}")
                        },
                        onAddToCart = {
                            carritoViewModel.addToCart(product)
                            snackbarMessage = "${product.nombre} agregado al carrito"
                            showSnackbar = true
                        }
                    )
                }
            }


            Spacer(Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate("inicio") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDAA541)),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Color.White
                )
                Spacer(Modifier.width(8.dp))
                Text("Volver a Inicio", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }


    LaunchedEffect(showSnackbar) {
        if (showSnackbar) {
            kotlinx.coroutines.delay(2000)
            showSnackbar = false
        }
    }
}

@Composable
fun DropdownMenuCategory(categories: List<String>, selected: String, onSelect: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Button(
            onClick = { expanded = true },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6E3F2F))
        ) {
            Text(
                if (selected == "all") "Todos los productos" else selected,
                color = Color.White
            )
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            categories.forEach { cat ->
                DropdownMenuItem(
                    text = { Text(if (cat == "all") "Todos los productos" else cat) },
                    onClick = {
                        onSelect(cat)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun ProductCard(product: Product, onProductClick: () -> Unit, onAddToCart: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5EAD3)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onProductClick() }
            ) {
                Image(
                    painter = painterResource(id = product.imageRes),
                    contentDescription = product.nombre,
                    modifier = Modifier.size(80.dp)
                )
                Spacer(Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        product.nombre,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        product.descripcion,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Precio: $${product.precio}",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF6E3F2F)
                    )
                }
            }

            Spacer(Modifier.height(12.dp))


            Button(
                onClick = onAddToCart,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFAD812C)),
                shape = RoundedCornerShape(50),
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(Modifier.width(8.dp))
                Text("Agregar al Carrito", color = Color.White)
            }
        }
    }
}