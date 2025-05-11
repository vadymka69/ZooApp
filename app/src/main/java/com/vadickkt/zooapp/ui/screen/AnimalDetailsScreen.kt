package com.vadickkt.zooapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vadickkt.zooapp.viemodel.AnimalDetailsViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AnimalDetailsScreen(
    viewModel: AnimalDetailsViewModel = hiltViewModel(),
    animalId: Long
) {
    val isLoading = viewModel.isLoading.collectAsState().value
    val animal = viewModel.animal.collectAsState().value
    val bird = viewModel.bird.collectAsState().value
    val reptile = viewModel.reptile.collectAsState().value

    LaunchedEffect(animalId) {
        viewModel.loadAnimalDetails(animalId)
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

                animal.dateOfBirthday?.let { date ->
                    Text(text = "Дата народження: ${formatDate(date)}")
                }

                Text(text = "Стать: ${animal.gender}")

                Text(
                    text = "Тип: ${
                        when {
                            animal.birdId != -1L -> "Птах"
                            animal.reptileId != -1L -> "Рептилія"
                            else -> "Невідомий"
                        }
                    }"
                )

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

private fun formatDate(date: Date?): String {
    return date?.let {
        val formatter = SimpleDateFormat("dd MMMM yyyy", Locale("uk", "UA"))
        formatter.format(it)
    } ?: "Невідомо"
}
