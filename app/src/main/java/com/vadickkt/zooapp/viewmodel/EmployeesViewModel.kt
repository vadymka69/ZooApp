package com.vadickkt.zooapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vadickkt.zooapp.database.dao.EmployeeDao
import com.vadickkt.zooapp.database.entities.Employee
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EmployeesViewModel @Inject constructor(
    private val employeeDao: EmployeeDao
): ViewModel() {
    private val _employees = mutableStateOf<List<Employee>>(emptyList())
    val employees: State<List<Employee>> = _employees

    init {
        loadEmployees()
    }

    fun loadEmployees() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val employeeList = employeeDao.getAllEmployees()
                withContext(Dispatchers.Main) {
                    _employees.value = employeeList
                }
            } catch (e: Exception) {

            }
        }
    }
}
