package com.vadickkt.zooapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vadickkt.zooapp.database.dao.EmployeeDao
import com.vadickkt.zooapp.database.entities.Employee
import com.vadickkt.zooapp.database.entities.JobType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddEmployeeViewModel @Inject constructor(
    private val employeeDao: EmployeeDao
) : ViewModel() {

    var name by mutableStateOf("")
    var phoneNumber by mutableStateOf("")
    var dateOfBirthday by mutableStateOf<Date?>(null)
    var jobType by mutableStateOf(JobType.Vet)

    fun saveEmployee(onSuccess: () -> Unit, onError: (String) -> Unit) {
        if (name.isBlank() || phoneNumber.isBlank() || dateOfBirthday == null) {
            onError("Усі поля обов'язкові")
            return
        }

        val newEmployee = Employee(
            employeeId = 0,
            name = name,
            dateOfBirthday = dateOfBirthday!!,
            phoneNumber = phoneNumber,
            jobType = jobType
        )

        viewModelScope.launch(Dispatchers.IO) {
            try {
                employeeDao.insertEmployee(newEmployee)
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Невідома помилка")
            }
        }
    }
}
