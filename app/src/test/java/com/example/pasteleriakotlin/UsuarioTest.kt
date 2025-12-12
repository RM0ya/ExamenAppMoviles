package com.example.pasteleriakotlin.JunitTest

import com.example.pasteleriakotlin.model.Usuario
import org.junit.Assert.assertEquals
import org.junit.Test

class UsuarioTest {

    @Test
    fun creaUsuarioConIdCero() {
        val usuario = Usuario(
            nombre = "Kevin",
            contrasena = "1234"
        )

        // Como el id es autoGenerate = true, por defecto debe empezar en 0
        assertEquals(0, usuario.id)
        assertEquals("Kevin", usuario.nombre)
        assertEquals("1234", usuario.contrasena)
    }

    @Test
    fun usuarioDuplicadoMismacontrasena() {
        val usuario = Usuario(
            id = 5,
            nombre = "Kevin",
            contrasena = "1234"
        )

        val usuarioActualizado = usuario.copy(nombre = "Kevin Alexander")

        assertEquals(5, usuarioActualizado.id)
        assertEquals("Kevin Alexander", usuarioActualizado.nombre)
        assertEquals("1234", usuarioActualizado.contrasena)
    }
}
