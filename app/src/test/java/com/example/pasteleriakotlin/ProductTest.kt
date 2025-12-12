package com.example.pasteleriakotlin.JunitTest

import com.example.pasteleriakotlin.model.Product
import org.junit.Assert.assertEquals
import org.junit.Test

class ProductTest {

    @Test
    fun creaProductoConValoresCorrectos() {
        val product = Product(
            id = 10,
            nombre = "Tres Leches",
            descripcion = "Pastel de tres leches con merengue",
            precio = 2500.0,
            categoria = "postre",
            imageRes = 123
        )

        assertEquals(10, product.id)
        assertEquals("Tres Leches", product.nombre)
        assertEquals("Pastel de tres leches con merengue", product.descripcion)
        assertEquals(2500.0, product.precio, 0.0)
        assertEquals("postre", product.categoria)
        assertEquals(123, product.imageRes)
    }

    @Test
    fun productoCopyMantieneCamposYPermiteCambioDePrecio() {
        val product = Product(
            id = 1,
            nombre = "Cheesecake",
            descripcion = "Pastel de queso",
            precio = 1000.0,
            categoria = "postre",
            imageRes = 0
        )

        val productConDescuento = product.copy(precio = 800.0)

        assertEquals(product.id, productConDescuento.id)
        assertEquals(product.nombre, productConDescuento.nombre)
        assertEquals(800.0, productConDescuento.precio, 0.0)
    }
}
