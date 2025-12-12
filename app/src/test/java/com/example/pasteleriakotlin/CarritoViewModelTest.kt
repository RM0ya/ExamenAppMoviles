package com.example.pasteleriakotlin.JunitTest

import com.example.pasteleriakotlin.model.Product
import com.example.pasteleriakotlin.viewmodel.CarritoViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

public class CarritoViewModelTest {

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
}
