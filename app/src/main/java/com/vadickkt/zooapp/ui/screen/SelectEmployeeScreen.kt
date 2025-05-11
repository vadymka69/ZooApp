package com.vadickkt.zooapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.vadickkt.zooapp.database.entities.JobType
import com.vadickkt.zooapp.ui.components.EmployeeItem
import com.vadickkt.zooapp.viewmodel.EmployeesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectEmployeeScreen(
    employeesViewModel: EmployeesViewModel = hiltViewModel(),
    navController: NavController,
    type: String
) {
    employeesViewModel.loadEmployees()
    val employees = employeesViewModel.employees.value.reversed()
    var searchQuery by remember { mutableStateOf("") }

    // Фільтруємо співробітників за типом
    val filteredEmployees = employees.filter { employee ->
        employee.name.contains(searchQuery, ignoreCase = true) &&
        when (type) {
            "VET" -> employee.jobType == JobType.Vet
            "CARETAKER" -> employee.jobType == JobType.Caretaker
            else -> true
        }
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

                Text(
                    text = when (type) {
                        "VET" -> "Виберіть ветеринара"
                        "CARETAKER" -> "Виберіть доглядача"
                        else -> "Виберіть працівника"
                    },
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    ) { paddingValues ->
        if (filteredEmployees.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text(
                    text = when (type) {
                        "VET" -> "Ветеринарів не знайдено"
                        "CARETAKER" -> "Доглядачів не знайдено"
                        else -> "Працівників не знайдено"
                    },
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(filteredEmployees) { employee ->
                    EmployeeItem(
                        employee = employee,
                        onClick = {
                            navController.previousBackStackEntry?.savedStateHandle?.set("selected_employee_id", employee.employeeId.toLong())
                            navController.previousBackStackEntry?.savedStateHandle?.set("employee_type", type)
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
} 