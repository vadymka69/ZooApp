package com.vadickkt.zooapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.vadickkt.zooapp.database.entities.Diet
import com.vadickkt.zooapp.navigation.Screen
import com.vadickkt.zooapp.viewmodel.AnimalDetailsViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalDetailsScreen(
    viewModel: AnimalDetailsViewModel = hiltViewModel(),
    animalId: Long,
    navController: NavController
) {
    val isLoading = viewModel.isLoading.collectAsState().value
    val animal = viewModel.animal.collectAsState().value
    val bird = viewModel.bird.collectAsState().value
    val reptile = viewModel.reptile.collectAsState().value
    val currentDiet = viewModel.currentDiet.collectAsState().value

    LaunchedEffect(animalId) {
        viewModel.loadAnimalDetails(animalId)
    }

    // Слухаємо результат вибору раціону
    LaunchedEffect(Unit) {
        navController.currentBackStackEntry?.savedStateHandle?.get<Long>("selected_diet_id")?.let { dietId ->
            viewModel.updateAnimalDiet(dietId)
            navController.currentBackStackEntry?.savedStateHandle?.remove<Long>("selected_diet_id")
        }
    }

    // Слухаємо результат вибору працівника
    LaunchedEffect(Unit) {
        navController.currentBackStackEntry?.savedStateHandle?.get<Long>("selected_employee_id")?.let { employeeId ->
            val employeeType = navController.currentBackStackEntry?.savedStateHandle?.get<String>("employee_type")
            when (employeeType) {
                "VET" -> viewModel.updateAnimalVet(employeeId)
                "CARETAKER" -> viewModel.updateAnimalCaretaker(employeeId)
            }
            navController.currentBackStackEntry?.savedStateHandle?.remove<Long>("selected_employee_id")
        }
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
        return
    }

    if (animal == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Тварину не знайдено")
        }
        return
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Деталі тварини",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Ім'я: ${animal.name}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(text = "Дата народження: ${formatDate(animal.dateOfBirthday)}")
                Text(text = "Стать: ${animal.gender}")

                Text(
                    text = "Тип: ${
                        when {
                            animal.birdId != -1L -> "Птах"
                            animal.reptileId != -1L -> "Рептилія"
                            else -> "Тварина"
                        }
                    }"
                )

                // Раціон
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Раціон: ${currentDiet?.name ?: "Не призначено"}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Button(onClick = { navController.navigate(Screen.Rations.route) }) {
                        Text("Змінити раціон")
                    }
                }

                // Ветеринар
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Ветеринар: ${viewModel.vet.value?.name ?: "Не призначено"}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Button(onClick = { 
                        navController.currentBackStackEntry?.savedStateHandle?.set("employee_type", "VET")
                        navController.navigate(Screen.SelectEmployee.createRoute("VET"))
                    }) {
                        Text("Призначити")
                    }
                }

                // Доглядач
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Доглядач: ${viewModel.caretaker.value?.name ?: "Не призначено"}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Button(onClick = { 
                        navController.currentBackStackEntry?.savedStateHandle?.set("employee_type", "CARETAKER")
                        navController.navigate(Screen.SelectEmployee.createRoute("CARETAKER"))
                    }) {
                        Text("Призначити")
                    }
                }

                if (animal.birdId != -1L && bird != null) {
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    Text(
                        text = "Інформація про птаха",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(text = "Країна: ${bird.countryName} (${bird.countryCode})")
                    Text(text = "Виліт: ${formatDate(bird.departureDate)}")
                    Text(text = "Приліт: ${formatDate(bird.arrivalDate)}")
                }

                if (animal.reptileId != -1L && reptile != null) {
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    Text(
                        text = "Інформація про рептилію",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(text = "Температура утримання: ${reptile.temperature}°C")
                    Text(
                        text = "Сплячка: ${formatDate(reptile.onsetOfHibernation)} – ${
                            formatDate(
                                reptile.endOfHibernation
                            )
                        }"
                    )
                }
            }
        }
    }
}

private fun formatDate(date: java.util.Date): String {
    val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return formatter.format(date)
}
