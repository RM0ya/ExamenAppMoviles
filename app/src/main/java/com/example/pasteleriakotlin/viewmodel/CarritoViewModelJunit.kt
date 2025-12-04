package com.example.pasteleriakotlin

import com.example.pasteleriakotlin.model.Product
import com.example.pasteleriakotlin.viewmodel.CarritoViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

class CarritoViewModelTest {

    private fun crearProductoDePrueba(): Product {
        return Product(
            id = 1,
            nombre = "Cheesecake",
            descripcion = "Pastel de queso con frutas",
            precio = 1000.0,
            categoria = "postre",
            imageRes = 0
        )
    }

    @Test
    fun iniciaVacio() {
        val viewModel = CarritoViewModel()

        assertEquals(0, viewModel.getTotalItems())
        assertEquals(0.0, viewModel.getTotalPrice(), 0.0)
    }

    @Test
    fun actualizaTotales() {
        val viewModel = CarritoViewModel()
        val producto = crearProductoDePrueba()


        assertEquals(0, viewModel.getTotalItems())
        assertEquals(0.0, viewModel.getTotalPrice(), 0.0)


        viewModel.addToCart(producto)
        assertEquals(1, viewModel.getTotalItems())
        assertEquals(1000.0, viewModel.getTotalPrice(), 0.0)


        viewModel.addToCart(producto)
        assertEquals(2, viewModel.getTotalItems())
        assertEquals(2000.0, viewModel.getTotalPrice(), 0.0)
    }

    @Test
    fun disminuyeCantidadLlegaACero() {
        val viewModel = CarritoViewModel()
        val producto = crearProductoDePrueba()


        viewModel.addToCart(producto)
        viewModel.addToCart(producto)
        assertEquals(2, viewModel.getTotalItems())
        assertEquals(2000.0, viewModel.getTotalPrice(), 0.0)

        viewModel.removeFromCart(productId = producto.id)
        assertEquals(1, viewModel.getTotalItems())
        assertEquals(1000.0, viewModel.getTotalPrice(), 0.0)


        viewModel.removeFromCart(productId = producto.id)
        assertEquals(0, viewModel.getTotalItems())
        assertEquals(0.0, viewModel.getTotalPrice(), 0.0)
    }

    @Test
    fun vaciaElCarrito() {
        val viewModel = CarritoViewModel()
        val producto = crearProductoDePrueba()

        viewModel.addToCart(producto)
        viewModel.addToCart(producto)
        assertEquals(2, viewModel.getTotalItems())

        viewModel.clearCart()
        assertEquals(0, viewModel.getTotalItems())
        assertEquals(0.0, viewModel.getTotalPrice(), 0.0)
    }
}
