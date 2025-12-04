package com.example.pasteleriakotlin.model

data class Product(

    val id: Int = 0,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val categoria: String,
    val imageRes: Int
)
