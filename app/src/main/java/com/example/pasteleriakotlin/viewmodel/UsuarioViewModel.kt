package com.example.pasteleriakotlin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pasteleriakotlin.data.localdao.UsuarioDAO
import com.example.pasteleriakotlin.data.localdao.Usuario
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UsuarioViewModel(private val dao: UsuarioDAO) : ViewModel() {

    val usuarios: StateFlow<List<Usuario>> = dao.obtenerUsuarios()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun insertarUsuario(nombre: String, contrasena: String) {
        viewModelScope.launch {
            dao.insertarUsuario(Usuario(nombre = nombre, contrasena = contrasena))
        }
    }

}