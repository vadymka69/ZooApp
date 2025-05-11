package com.vadickkt.zooapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vadickkt.zooapp.database.entities.Employee
import java.text.SimpleDateFormat
import java.util.Locale

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
                text = "Посада: ${employee.jobType.name}",
                style = MaterialTheme.typography.bodyMedium
            )
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