package com.example.pasteleriakotlin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pasteleriakotlin.data.network.BackendApiClient
import com.example.pasteleriakotlin.data.network.ExternalApiClient
import com.example.pasteleriakotlin.data.network.LoginRequest
import com.example.pasteleriakotlin.data.network.LoginResponse
import com.example.pasteleriakotlin.data.network.PostDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ApiViewModel : ViewModel() {


    private val _postExterno = MutableStateFlow<PostDto?>(null)
    val postExterno: StateFlow<PostDto?> = _postExterno
    private val _loginResponse = MutableStateFlow<LoginResponse?>(null)
    val loginResponse: StateFlow<LoginResponse?> = _loginResponse
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun cargarPostExterno() {
        viewModelScope.launch {
            try {
                _error.value = null
                _postExterno.value = ExternalApiClient.api.getExamplePost()
            } catch (e: Exception) {
                _error.value = "Error API externa: ${e.message}"
            }
        }
    }

    fun loginBackend(nombre: String, contrasena: String) {
        viewModelScope.launch {
            try {
                _error.value = null
                val request = LoginRequest(nombre = nombre, contrasena = contrasena)
                _loginResponse.value = BackendApiClient.api.login(request)
            } catch (e: Exception) {
                _error.value = "Error backend: ${e.message}"
            }
        }
    }
}
