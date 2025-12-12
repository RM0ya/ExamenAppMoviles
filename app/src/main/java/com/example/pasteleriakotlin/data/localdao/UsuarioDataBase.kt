package com.example.pasteleriakotlin.data.localdao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pasteleriakotlin.model.Product


@Database(
    entities = [Usuario::class, Product::class],
    version = 2,
    exportSchema = false
)
abstract class UsuarioDataBase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDAO
    abstract fun productDao(): ProductDAO


    companion object {
        @Volatile
        private var INSTANCE: UsuarioDataBase? = null

        fun getDatabase(context: Context): UsuarioDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UsuarioDataBase::class.java,
                    "usuario_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
