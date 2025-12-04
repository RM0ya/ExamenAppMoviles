package com.example.pasteleriakotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.pasteleriakotlin.data.localdao.UsuarioDataBase
import com.example.pasteleriakotlin.ui.screen.CarritoScreen
import com.example.pasteleriakotlin.ui.screen.InicioScreen
import com.example.pasteleriakotlin.ui.screen.LoginScreen
import com.example.pasteleriakotlin.ui.screen.ProductDetailScreen
import com.example.pasteleriakotlin.ui.screen.ProductsScreen
import com.example.pasteleriakotlin.ui.screen.RegistroScreen
import com.example.pasteleriakotlin.ui.screen.SplashScreen
import com.example.pasteleriakotlin.viewmodel.CarritoViewModel
import com.example.pasteleriakotlin.viewmodel.UsuarioViewModel
import com.example.pasteleriakotlin.ui.screen.ApiTestScreen
import com.example.pasteleriakotlin.ui.screen.MealsScreen
import com.example.pasteleriakotlin.ui.screen.BackendLoginScreen
import com.example.pasteleriakotlin.ui.admins.AdminHomeScreen
import com.example.pasteleriakotlin.ui.admins.AdminPedidosScreen
import com.example.pasteleriakotlin.ui.admins.AdminProductosScreen
import com.example.pasteleriakotlin.ui.admins.GestCategoriasScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = UsuarioDataBase.getDatabase(this)
        val usuarioDao = database.usuarioDao()

        setContent {
            val navController = rememberNavController()


            val usuarioViewModel: UsuarioViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return UsuarioViewModel(usuarioDao) as T
                    }
                }
            )


            val carritoViewModel: CarritoViewModel = viewModel()

            NavHost(navController, startDestination = "splash") {

                composable("backendLogin") {
                    BackendLoginScreen()
                }

                composable("meals") {
                    MealsScreen()
                }
                composable("apiTest") {
                    ApiTestScreen()
                }
                composable("splash") {
                    SplashScreen(navController = navController)
                }

                composable("inicio") {
                    InicioScreen(navController = navController)
                }
                composable("adminHome") {
                    AdminHomeScreen(navController = navController)
                }
                composable("admin/products") {
                    AdminProductosScreen(navController = navController)
                }
                composable("admin/orders") {
                    AdminPedidosScreen(navController = navController)
                }
                composable("admin/categories") {
                    GestCategoriasScreen(navController = navController)
                }
                composable("admin/users") {
                    AdminHomeScreen(navController = navController)
                }


                composable("registro") {
                    RegistroScreen(viewModel = usuarioViewModel, navController = navController)
                }

                composable("login"){
                    LoginScreen( navController = navController)
                }


                composable("products") {
                    ProductsScreen(navController = navController, carritoViewModel = carritoViewModel)
                }

                composable("carrito") {
                    CarritoScreen(viewModel = carritoViewModel, navController = navController)
                }

                composable(
                    "productDetail/{productId}",
                    arguments = listOf(navArgument("productId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val id = backStackEntry.arguments?.getInt("productId") ?: 0
                    ProductDetailScreen(
                        productId = id,
                        navController = navController,
                        carritoViewModel = carritoViewModel
                    )
                }
            }
        }
    }
}