package com.example.pasteleriakotlin.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pasteleriakotlin.R

@Composable
fun InicioScreen(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF6F0DE)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {

            Box(
                modifier = Modifier
                    .background(Color(0xFFF5EAD3), shape = RoundedCornerShape(20.dp))
                    .padding(32.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo1),
                    contentDescription = "Logo Pastelería",
                    modifier = Modifier.size(180.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))


            Text(
                text = "Bienvenido",
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFFAD812C),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Deliciosos momentos te esperan",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF6E3F2F),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))


            MenuButton(
                text = "Iniciar Sesión",
                icon = Icons.Filled.Login,
                backgroundColor = Color(0xFF6E3F2F),
                onClick = { navController.navigate("login") }
            )

            Spacer(modifier = Modifier.height(16.dp))


            MenuButton(
                text = "Registrarse",
                icon = Icons.Filled.PersonAdd,
                backgroundColor = Color(0xFFDAA541),
                onClick = { navController.navigate("registro") }
            )

            Spacer(modifier = Modifier.height(16.dp))


            MenuButton(
                text = "Ver Productos",
                icon = Icons.Filled.ShoppingBag,
                backgroundColor = Color(0xFFAD812C),
                onClick = { navController.navigate("products") }
            )

            Spacer(modifier = Modifier.height(32.dp))


            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5EAD3)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "🍰 Descubre nuestra variedad de pasteles, tortas y postres artesanales",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF6E3F2F),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun MenuButton(
    text: String,
    icon: ImageVector,
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
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

