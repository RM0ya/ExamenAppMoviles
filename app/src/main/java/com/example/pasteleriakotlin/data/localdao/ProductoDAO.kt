package com.example.pasteleriakotlin.data.localdao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pasteleriakotlin.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarProducto(producto: Product)

    @Query("SELECT * FROM productos")
    fun obtenerProductos(): Flow<List<Product>>
}
