package com.vadickkt.zooapp.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vadickkt.zooapp.database.entities.Diet
import com.vadickkt.zooapp.database.entities.DietType
import com.vadickkt.zooapp.viewmodel.RationsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RationsScreen(
    viewModel: RationsViewModel = hiltViewModel(),
    onRationSelected: (Diet) -> Unit
) {
    var showAddDialog by remember { mutableStateOf(false) }
    val rations by viewModel.rations.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadRations()
    }
    
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Раціони",
                style = MaterialTheme.typography.headlineMedium
            )
            Button(onClick = { showAddDialog = true }) {
                Text("Додати раціон")
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(rations) { ration ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onRationSelected(ration) }
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = ration.name,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "Тип: ${ration.type.name}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        if (ration.description.isNotEmpty()) {
                            Text(
                                text = ration.description,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AddRationDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { name, type, description ->
                viewModel.addRation(name, type, description)
                showAddDialog = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddRationDialog(
    onDismiss: () -> Unit,
    onConfirm: (name: String, type: DietType, description: String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var type by remember { mutableStateOf(DietType.STANDARD) }
    var description by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Додати новий раціон") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Назва раціону") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = it }
                ) {
                    OutlinedTextField(
                        value = type.name,
                        onValueChange = { },
                        readOnly = true,
                        label = { Text("Тип раціону") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DietType.entries.forEach { dietType ->
                            DropdownMenuItem(
                                text = { Text(dietType.name) },
                                onClick = {
                                    type = dietType
                                    expanded = false
                                }
                            )
                        }
                    }
                }
                
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Опис (необов'язково)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(name, type, description) },
                enabled = name.isNotBlank()
            ) {
                Text("Додати")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Скасувати")
            }
        }
    )
} 