package com.example.pasteleriakotlin.ui.admins

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminProductosScreen(navController: NavController) {

    var apiMeals by remember { mutableStateOf<List<AdminMealDto>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }


    LaunchedEffect(Unit) {
        isLoading = true
        try {
            val response = AdminMealDbClient.api.getMealsByCategory("Dessert")
            apiMeals = response.meals ?: emptyList()
        } catch (e: Exception) {
            errorMessage = "Error al cargar productos desde la API: ${e.localizedMessage}"
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestionar Productos") },
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
                    text = "Productos locales",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = Color(0xFF6E3F2F),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                localAdminProducts.forEach { product ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5EAD3)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Text(
                                text = product.name,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF6E3F2F)
                            )
                            Text(
                                text = product.description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF6E3F2F)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Categoría: ${product.category}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF8B6F47)
                            )
                            Text(
                                text = "Precio: $${product.price}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF8B6F47)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))


                Text(
                    text = "Productos desde la API (TheMealDB)",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = Color(0xFF6E3F2F),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                when {
                    isLoading -> {
                        CircularProgressIndicator()
                    }
                    errorMessage != null -> {
                        Text(
                            text = errorMessage ?: "",
                            color = Color.Red,
                            textAlign = TextAlign.Center
                        )
                    }
                    apiMeals.isEmpty() -> {
                        Text(
                            text = "No se encontraron productos desde la API.",
                            color = Color(0xFF6E3F2F)
                        )
                    }
                    else -> {
                        apiMeals.forEach { meal ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5EAD3)),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Column(Modifier.padding(12.dp)) {
                                    Text(
                                        text = meal.name,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF6E3F2F)
                                    )
                                    meal.category?.let {
                                        Text(
                                            text = "Categoría API: $it",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color(0xFF8B6F47)
                                        )
                                    }
                                    meal.area?.let {
                                        Text(
                                            text = "Origen: $it",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color(0xFF8B6F47)
                                        )
                                    }
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



data class AdminProduct(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val category: String
)


val localAdminProducts = listOf(
    AdminProduct(1, "Cheesecake", "Pastel de queso con frutas", 7500.0, "postre"),
    AdminProduct(2, "Brownie", "Bizcocho húmedo de chocolate", 5500.0, "dulce"),
    AdminProduct(3, "Mouse Chocolate", "Mouse de chocolate amargo", 6200.0, "dulce"),
    AdminProduct(4,"Torta Cuadrada Chocolate","Torta de bizcocho de vainilla cubierta de chocolate",10520.0,"postre"),
    AdminProduct(5,"Torta circular Chocolate","Torta de bizcocho de vainilla cubierta de chocolate",12520.0,"postre"),
    AdminProduct(6,"Torta Cuadrada Frutas","Torta de bizcocho de vainilla cubierta de Frutas",12990.0,"postre"),
    AdminProduct(7,"Torta Matrimonial","Torta de 3 pisos perfecta para matrimonio",25990.0,"postre"),
    AdminProduct(8,"Torta de naranja con crema pastelera","Torta de naranja cubierta de crema pastelera",15990.0,"postre"),
    AdminProduct(9,"Torta Vegana","Torta 100% vegana sin uso de materias primas animales",18990.0,"postre"),
    AdminProduct(10,"Torta Circular Vainilla","Torta clasica de vainilla para compartir en las tardes",10990.0,"postre")
)



data class AdminMealDto(
    @SerializedName("idMeal") val id: String,
    @SerializedName("strMeal") val name: String,
    @SerializedName("strMealThumb") val thumbnail: String?,
    @SerializedName("strCategory") val category: String?,
    @SerializedName("strArea") val area: String?,
    @SerializedName("strInstructions") val instructions: String?
)

data class AdminMealsResponse(
    @SerializedName("meals") val meals: List<AdminMealDto>?
)

interface AdminMealDbApi {

    // /search.php?s=cake
    @GET("search.php")
    suspend fun searchMealsByName(
        @Query("s") name: String
    ): AdminMealsResponse

    // /filter.php?c=Dessert
    @GET("filter.php")
    suspend fun getMealsByCategory(
        @Query("c") category: String
    ): AdminMealsResponse

    // /random.php
    @GET("random.php")
    suspend fun getRandomMeal(): AdminMealsResponse
}

object AdminMealDbClient {

    private val logger = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(logger)
        .build()

    private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

    val api: AdminMealDbApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AdminMealDbApi::class.java)
    }
}
