package com.example.pasteleriakotlin.data.ViewModelsApi

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pasteleriakotlin.data.network.BackendApiClient
import com.example.pasteleriakotlin.data.network.LoginRequest
import com.example.pasteleriakotlin.data.network.LoginResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BackendLoginViewModel : ViewModel() {

    private val _loginResponse = MutableStateFlow<LoginResponse?>(null)
    val loginResponse: StateFlow<LoginResponse?> = _loginResponse

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun login(nombre: String, contrasena: String) {
        viewModelScope.launch {
            try {
                _loading.value = true
                _error.value = null

                val request = LoginRequest(nombre = nombre, contrasena = contrasena)
                val response = BackendApiClient.api.login(request)

                _loginResponse.value = response
            } catch (e: Exception) {
                Log.e("BackendLoginVM", "Error en login", e)
                _error.value = "Error en login: ${e.message}"
                _loginResponse.value = null
            } finally {
                _loading.value = false
            }
        }
    }
}