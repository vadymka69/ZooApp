package com.vadickkt.zooapp.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.vadickkt.zooapp.database.entities.Gender
import com.vadickkt.zooapp.navigation.Screen
import com.vadickkt.zooapp.viewmodel.AnimalsViewModel
import kotlinx.serialization.Serializable

@Composable
fun AnimalsScreen(
    viewModel: AnimalsViewModel = hiltViewModel(),
    navController: NavController,
    onAddNew: () -> Unit
) {
    viewModel.loadAnimals()
    val animals = viewModel.animals.value.reversed()
    var searchQuery by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("Всі") }

    val filteredAnimals = animals.filter {
        it.name.contains(searchQuery, ignoreCase = true) &&
                (selectedType == "Всі" ||
                        (selectedType == "Пташка" && it.birdId != -1L) ||
                        (selectedType == "Рептилія" && it.reptileId != -1L))
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
                    val types = listOf("Всі", "Пташка", "Рептилія")
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
                        .clickable {
                            navController.navigate(Screen.AnimalDetails.createRoute(animal.animalId))
                        }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = animal.name, style = MaterialTheme.typography.titleMedium)
                        val state = if (animal.gender == Gender.MALE) {
                            "Чоловіча"
                        } else if (animal.gender == Gender.FEMALE) {
                            "Жіноча"
                        } else {
                            "Невідомо"
                        }
                        Text(
                            text = "Стать: $state, Тип: ${
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