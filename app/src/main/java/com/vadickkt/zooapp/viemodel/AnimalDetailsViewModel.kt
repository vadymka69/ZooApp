package com.vadickkt.zooapp.viemodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vadickkt.zooapp.database.dao.AnimalDao
import com.vadickkt.zooapp.database.dao.BirdDao
import com.vadickkt.zooapp.database.dao.ReptileDao
import com.vadickkt.zooapp.database.entities.Animal
import com.vadickkt.zooapp.database.entities.Bird
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
    private val reptileDao: ReptileDao
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _animal = MutableStateFlow<Animal?>(null)
    val animal: StateFlow<Animal?> = _animal

    private val _bird = MutableStateFlow<Bird?>(null)
    val bird: StateFlow<Bird?> = _bird

    private val _reptile = MutableStateFlow<Reptile?>(null)
    val reptile: StateFlow<Reptile?> = _reptile

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
                }
            } finally {
                _isLoading.value = false
            }
        }
    }
}
