package com.vadickkt.zooapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vadickkt.zooapp.database.dao.AnimalDao
import com.vadickkt.zooapp.database.dao.BirdDao
import com.vadickkt.zooapp.database.dao.DietDao
import com.vadickkt.zooapp.database.dao.EmployeeDao
import com.vadickkt.zooapp.database.dao.ReptileDao
import com.vadickkt.zooapp.database.entities.Animal
import com.vadickkt.zooapp.database.entities.Bird
import com.vadickkt.zooapp.database.entities.Diet
import com.vadickkt.zooapp.database.entities.Employee
import com.vadickkt.zooapp.database.entities.Reptile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimalDetailsViewModel @Inject constructor(
    private val animalDao: AnimalDao,
    private val birdDao: BirdDao,
    private val reptileDao: ReptileDao,
    private val dietDao: DietDao,
    private val employeeDao: EmployeeDao
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _animal = MutableStateFlow<Animal?>(null)
    val animal: StateFlow<Animal?> = _animal

    private val _bird = MutableStateFlow<Bird?>(null)
    val bird: StateFlow<Bird?> = _bird

    private val _reptile = MutableStateFlow<Reptile?>(null)
    val reptile: StateFlow<Reptile?> = _reptile

    private val _currentDiet = MutableStateFlow<Diet?>(null)
    val currentDiet: StateFlow<Diet?> = _currentDiet

    private val _vet = MutableStateFlow<Employee?>(null)
    val vet: StateFlow<Employee?> = _vet

    private val _caretaker = MutableStateFlow<Employee?>(null)
    val caretaker: StateFlow<Employee?> = _caretaker

    fun loadAnimalDetails(animalId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                val animalFromDb = animalDao.getAnimalById(animalId)
                _animal.value = animalFromDb

                animalFromDb?.let { animal ->
                    if (animal.birdId != -1L) {
                        _bird.value = birdDao.getBirdById(animal.birdId)
                    }
                    if (animal.reptileId != -1L) {
                        _reptile.value = reptileDao.getReptileById(animal.reptileId)
                    }
                    if (animal.dietId != -1L) {
                        _currentDiet.value = dietDao.getDietById(animal.dietId)
                    }
                    if (animal.vetId != -1L) {
                        _vet.value = employeeDao.getEmployeeById(animal.vetId)
                    }
                    if (animal.caretakerId != -1L) {
                        _caretaker.value = employeeDao.getEmployeeById(animal.caretakerId)
                    }
                }
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateAnimalDiet(dietId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _animal.value?.let { animal ->
                val updatedAnimal = animal.copy(dietId = dietId)
                animalDao.updateAnimal(updatedAnimal)
                _animal.value = updatedAnimal
                _currentDiet.value = dietDao.getDietById(dietId)
            }
        }
    }

    fun updateAnimalVet(employeeId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _animal.value?.let { animal ->
                val updatedAnimal = animal.copy(vetId = employeeId)
                animalDao.updateAnimal(updatedAnimal)
                _animal.value = updatedAnimal
                _vet.value = employeeDao.getEmployeeById(employeeId)
            }
        }
    }

    fun updateAnimalCaretaker(employeeId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _animal.value?.let { animal ->
                val updatedAnimal = animal.copy(caretakerId = employeeId)
                animalDao.updateAnimal(updatedAnimal)
                _animal.value = updatedAnimal
                _caretaker.value = employeeDao.getEmployeeById(employeeId)
            }
        }
    }
}
