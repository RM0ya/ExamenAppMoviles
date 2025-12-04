package com.example.pasteleriakotlin.viewmodel

import androidx.lifecycle.ViewModel
import com.example.pasteleriakotlin.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class CartItem(
    val producto: Product,
    val cantidad: Int
)

class CarritoViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<Map<Int, CartItem>>(emptyMap())
    val cartItems: StateFlow<Map<Int, CartItem>> = _cartItems.asStateFlow()

    fun addToCart(product: Product) {
        val currentItems = _cartItems.value.toMutableMap()
        val existingItem = currentItems[product.id]

        if (existingItem != null) {
            currentItems[product.id] = existingItem.copy(cantidad = existingItem.cantidad + 1)
        } else {
            currentItems[product.id] = CartItem(product, 1)
        }

        _cartItems.value = currentItems
    }

    fun removeFromCart(productId: Int) {
        val currentItems = _cartItems.value.toMutableMap()
        val existingItem = currentItems[productId]

        if (existingItem != null) {
            if (existingItem.cantidad > 1) {
                currentItems[productId] = existingItem.copy(cantidad = existingItem.cantidad - 1)
            } else {
                currentItems.remove(productId)
            }
        }

        _cartItems.value = currentItems
    }

    fun clearCart() {
        _cartItems.value = emptyMap()
    }

    fun getTotalItems(): Int {
        return _cartItems.value.values.sumOf { it.cantidad }
    }

    fun getTotalPrice(): Double {
        return _cartItems.value.values.sumOf { it.producto.precio * it.cantidad }
    }
}