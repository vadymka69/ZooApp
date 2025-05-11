package com.vadickkt.zooapp.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vadickkt.zooapp.database.entities.Animal
import com.vadickkt.zooapp.viemodel.AnimalsViewModel

@Composable
fun AnimalsScreen(
    viewModel: AnimalsViewModel = hiltViewModel(),
    onAddNew: () -> Unit,
    onDetails: (Animal) -> Unit,
) {
    viewModel.loadAnimals()
    val animals = viewModel.animals.value.reversed()
    var searchQuery by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("All") }

    val filteredAnimals = animals.filter {
        it.name.contains(searchQuery, ignoreCase = true) &&
                (selectedType == "All" ||
                        (selectedType == "Bird" && it.birdId != -1L) ||
                        (selectedType == "Reptile" && it.reptileId != -1L))
    }

    Scaffold(
        topBar = {
            Column(modifier = Modifier.padding(8.dp)) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Пошук за іменем") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    val types = listOf("All", "Bird", "Reptile")
                    types.forEach { type ->
                        FilterChip(
                            selected = selectedType == type,
                            onClick = { selectedType = type },
                            label = { Text(type) }
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddNew) {
                Icon(Icons.Default.Add, contentDescription = "Додати тварину")
            }
        },
    ) { paddingValues ->
        LazyColumn(contentPadding = paddingValues) {
            items(filteredAnimals.size) { index ->
                val animal = filteredAnimals[index]

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { onDetails(animal) }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = animal.name, style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = "Стать: ${animal.gender}, Тип: ${
                                when {
                                    animal.birdId != -1L -> "Пташка"
                                    animal.reptileId != -1L -> "Рептилія"
                                    else -> "Тварина"
                                }
                            }"
                        )
                    }
                }
            }
        }
    }
}
