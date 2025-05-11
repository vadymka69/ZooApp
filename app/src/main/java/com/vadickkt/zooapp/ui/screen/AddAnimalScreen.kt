package com.vadickkt.zooapp.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vadickkt.zooapp.database.entities.Gender
import com.vadickkt.zooapp.ui.components.DatePickerTextField
import com.vadickkt.zooapp.viewmodel.AddAnimalViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAnimalScreen(
    viewModel: AddAnimalViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val dateFormat = remember { SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Створення тварини", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = viewModel.name,
            onValueChange = { viewModel.name = it },
            label = { Text("Ім’я") },
            modifier = Modifier.fillMaxWidth()
        )

        DatePickerTextField(
            label = "Дата народження",
            date = viewModel.birthday?.let { dateFormat.format(it) } ?: "",
            onDateChange = {
                viewModel.birthday = runCatching { dateFormat.parse(it) }.getOrNull()
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text("Стать:")
        Gender.entries.forEach {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = viewModel.gender == it,
                    onClick = { viewModel.gender = it }
                )
                Text(it.name)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text("Тип тварини:")
        listOf("None", "Bird", "Reptile").forEach {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = viewModel.animalType == it,
                    onClick = { viewModel.animalType = it }
                )
                Text(it)
            }
        }

        if (viewModel.animalType == "Bird") {
            OutlinedTextField(
                value = viewModel.countryName,
                onValueChange = { viewModel.countryName = it },
                label = { Text("Країна") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = viewModel.countryCode,
                onValueChange = { viewModel.countryCode = it },
                label = { Text("Код країни") },
                modifier = Modifier.fillMaxWidth()
            )
            DatePickerTextField(
                label = "Дата відправлення",
                date = viewModel.departureDate?.let { dateFormat.format(it) } ?: "",
                onDateChange = {
                    viewModel.departureDate = runCatching { dateFormat.parse(it) }.getOrNull()
                },
                modifier = Modifier.fillMaxWidth()
            )
            DatePickerTextField(
                label = "Дата прибуття",
                date = viewModel.arrivalDate?.let { dateFormat.format(it) } ?: "",
                onDateChange = {
                    viewModel.arrivalDate = runCatching { dateFormat.parse(it) }.getOrNull()
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (viewModel.animalType == "Reptile") {
            OutlinedTextField(
                value = viewModel.temperature,
                onValueChange = { viewModel.temperature = it },
                label = { Text("Температура") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            DatePickerTextField(
                label = "Початок сплячки",
                date = viewModel.onsetOfHibernation?.let { dateFormat.format(it) } ?: "",
                onDateChange = {
                    viewModel.onsetOfHibernation = runCatching { dateFormat.parse(it) }.getOrNull()
                },
                modifier = Modifier.fillMaxWidth()
            )
            DatePickerTextField(
                label = "Кінець сплячки",
                date = viewModel.endOfHibernation?.let { dateFormat.format(it) } ?: "",
                onDateChange = {
                    viewModel.endOfHibernation = runCatching { dateFormat.parse(it) }.getOrNull()
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.saveAnimal(
                    onSuccess = {
                        Toast.makeText(context, "Тварину збережено", Toast.LENGTH_SHORT).show()
                    },
                    onError = {
                        Toast.makeText(context, "Помилка: $it", Toast.LENGTH_LONG).show()
                    }
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Зберегти")
        }
    }
}
