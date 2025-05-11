package com.vadickkt.zooapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.vadickkt.zooapp.database.entities.Employee
import com.vadickkt.zooapp.database.entities.JobType
import com.vadickkt.zooapp.ui.components.EmployeeItem
import com.vadickkt.zooapp.viewmodel.EmployeesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeesScreen(
    employeesViewModel: EmployeesViewModel = hiltViewModel(),
    onDetails: (Employee) -> Unit,
    onAdd: () -> Unit,
    navController: NavController
) {
    employeesViewModel.loadEmployees()
    val employees = employeesViewModel.employees.value.reversed()
    var searchQuery by remember { mutableStateOf("") }
    var selectedJobType by remember { mutableStateOf<JobType?>(null) }

    // Фільтруємо співробітників
    val filteredEmployees = employees.filter { employee ->
        val matchesSearch = employee.name.contains(searchQuery, ignoreCase = true)
        val matchesType = selectedJobType?.let { employee.jobType == it } ?: true
        matchesSearch && matchesType
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

                // Фільтри за типом працівника
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        FilterChip(
                            selected = selectedJobType == null,
                            onClick = { selectedJobType = null },
                            label = { Text("Всі") }
                        )
                    }
                    item {
                        FilterChip(
                            selected = selectedJobType == JobType.Vet,
                            onClick = { selectedJobType = JobType.Vet },
                            label = { Text("Ветеринари") }
                        )
                    }
                    item {
                        FilterChip(
                            selected = selectedJobType == JobType.Caretaker,
                            onClick = { selectedJobType = JobType.Caretaker },
                            label = { Text("Доглядачі") }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Список співробітників",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAdd) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Додати працівника")
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
                    text = "Працівників не знайдено",
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
                        onClick = { onDetails(employee) }
                    )
                }
            }
        }
    }
}
