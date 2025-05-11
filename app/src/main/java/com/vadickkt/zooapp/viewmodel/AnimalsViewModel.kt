package com.vadickkt.zooapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vadickkt.zooapp.database.dao.AnimalDao
import com.vadickkt.zooapp.database.entities.Animal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AnimalsViewModel @Inject constructor(
    private val animalDao: AnimalDao
) : ViewModel() {

    private val _animals = mutableStateOf<List<Animal>>(emptyList())
    val animals: State<List<Animal>> = _animals

    init {
        loadAnimals()
    }

    fun loadAnimals() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val animalsList = animalDao.getAllAnimals()
                withContext(Dispatchers.Main) {
                    _animals.value = animalsList
                }
            } catch (e: Exception) {
                // Логування або обробка помилок
            }
        }
    }
}
