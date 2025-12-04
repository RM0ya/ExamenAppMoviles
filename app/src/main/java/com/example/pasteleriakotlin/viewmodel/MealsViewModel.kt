package com.example.pasteleriakotlin.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pasteleriakotlin.data.network.MealDto
import com.example.pasteleriakotlin.data.network.TheMealDbClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MealsViewModel : ViewModel() {

    private val _desserts = MutableStateFlow<List<MealDto>>(emptyList())
    val desserts: StateFlow<List<MealDto>> = _desserts

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadDesserts() {
        viewModelScope.launch {
            try {
                _error.value = null
                val response = TheMealDbClient.api.getMealsByCategory("Dessert")
                _desserts.value = response.meals ?: emptyList()
            } catch (e: Exception) {
                Log.e("MealsViewModel", "Error al cargar postres", e)
                _error.value = "Error al cargar postres: ${e.message}"
                _desserts.value = emptyList()
            }
        }
    }

    fun searchByName(name: String) {
        viewModelScope.launch {
            try {
                _error.value = null
                val response = TheMealDbClient.api.searchMealsByName(name)
                _desserts.value = response.meals ?: emptyList()
            } catch (e: Exception) {
                Log.e("MealsViewModel", "Error al buscar", e)
                _error.value = "Error al buscar: ${e.message}"
                _desserts.value = emptyList()
            }
        }
    }
}
