package com.vadickkt.zooapp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vadickkt.zooapp.viewmodel.MarriageViewModel

@Composable
fun MarriageScreen(viewModel: MarriageViewModel = hiltViewModel()) {
    val marriages by viewModel.marriages.collectAsState()
    var partner1 by remember { mutableStateOf("") }
    var partner2 by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Створити нове подружжя", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = partner1,
            onValueChange = { partner1 = it },
            label = { Text("ID Партнера 1") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = partner2,
            onValueChange = { partner2 = it },
            label = { Text("ID Партнера 2") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val p1 = partner1.toIntOrNull()
                val p2 = partner2.toIntOrNull()
                if (p1 != null && p2 != null) {
                    viewModel.addMarriage(p1, p2)
                    partner1 = ""
                    partner2 = ""
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Додати шлюб")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Список шлюбів", style = MaterialTheme.typography.titleMedium)
        LazyColumn {
            items(marriages.size) { index ->
                val marriage = marriages[index]
                Text("👫 Шлюб ID: ${marriage.marriageId}, Партнери: ${marriage.partner1Id} & ${marriage.partner2Id}")
                Divider(modifier = Modifier.padding(vertical = 4.dp))
            }
        }
    }
}
