package com.vadickkt.zooapp.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vadickkt.zooapp.database.entities.Employee
import com.vadickkt.zooapp.viemodel.EmployeesViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeesScreen(
    employeesViewModel: EmployeesViewModel = hiltViewModel(),
    onDetails: (Employee) -> Unit,
    onAdd: () -> Unit
) {
    val employees = employeesViewModel.employees.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Працівники", style = MaterialTheme.typography.titleLarge) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAdd) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Додати працівника")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(employees) { employee ->
                EmployeeItem(employee = employee, onClick = { onDetails(employee) })
            }
        }
    }
}

@Composable
fun EmployeeItem(employee: Employee, onClick: () -> Unit) {
    val dateFormat = remember { SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()) }

    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = employee.name,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Дата народження: ${dateFormat.format(employee.dateOfBirthday)}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Телефон: ${employee.phoneNumber}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
