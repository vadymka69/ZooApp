package com.vadickkt.zooapp.ui.components

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import java.util.*

@Composable
fun DatePickerTextField(
    label: String,
    date: String,
    onDateChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = remember {
        DatePickerDialog(context, { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            val selectedDate = String.format("%02d.%02d.%d", selectedDayOfMonth, selectedMonth + 1, selectedYear)
            onDateChange(selectedDate)
        }, year, month, day)
    }

    OutlinedTextField(
        value = date,
        onValueChange = {},
        label = { Text(label) },
        modifier = modifier
            .clickable { datePickerDialog.show() },
        enabled = false,
        readOnly = true
    )
}
