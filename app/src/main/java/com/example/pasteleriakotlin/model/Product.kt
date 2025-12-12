package com.example.pasteleriakotlin.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productos")

data class Product(

    @PrimaryKey(autoGenerate = true) val id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val categoria: String,
    val imageRes: Int
)
