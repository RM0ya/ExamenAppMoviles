package com.example.pasteleriakotlin.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pasteleriakotlin.viewmodel.MealsViewModel

@Composable
fun MealsScreen(
    mealsViewModel: MealsViewModel = viewModel()
) {
    val desserts by mealsViewModel.desserts.collectAsState()
    val error by mealsViewModel.error.collectAsState()
    val searchText = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Button(onClick = { mealsViewModel.loadDesserts() }) {
            Text("Cargar postres (Dessert) de TheMealDB")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = searchText.value,
            onValueChange = { searchText.value = it },
            label = { Text("Buscar por nombre (ej: cake)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { mealsViewModel.searchByName(searchText.value) }) {
            Text("Buscar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        error?.let {
            Text(text = it, color = Color.Red)
            Spacer(modifier = Modifier.height(8.dp))
        }

        LazyColumn {
            items(desserts) { meal ->
                Column(modifier = Modifier.padding(vertical = 4.dp)) {
                    Text(text = meal.name)
                    Text(text = "id: ${meal.id}")
                }
            }
        }
    }
}
