package com.example.pasteleriakotlin.JunitTest

import com.example.pasteleriakotlin.model.Product
import com.example.pasteleriakotlin.viewmodel.CarritoViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

class CarritoViewModelMultipleProductsTest {

    private fun crearProducto(id: Int, precio: Double): Product {
        return Product(
            id = id,
            nombre = "Producto $id",
            descripcion = "Descripción",
            precio = precio,
            categoria = "postre",
            imageRes = 0
        )
    }

    @Test
    fun agregarProductosCantidades() {
        val viewModel = CarritoViewModel()

        val producto1 = crearProducto(id = 1, precio = 1000.0)
        val producto2 = crearProducto(id = 2, precio = 1500.0)

        viewModel.addToCart(producto1)
        viewModel.addToCart(producto2)
        viewModel.addToCart(producto2)

        assertEquals(3, viewModel.getTotalItems())
        assertEquals(4000.0, viewModel.getTotalPrice(), 0.0)
    }
}
