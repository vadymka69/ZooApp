package com.vadickkt.zooapp.viemodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vadickkt.zooapp.database.dao.AnimalDao
import com.vadickkt.zooapp.database.dao.BirdDao
import com.vadickkt.zooapp.database.dao.ReptileDao
import com.vadickkt.zooapp.database.entities.Animal
import com.vadickkt.zooapp.database.entities.Bird
import com.vadickkt.zooapp.database.entities.Gender
import com.vadickkt.zooapp.database.entities.Reptile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddAnimalViewModel @Inject constructor(
    private val animalDao: AnimalDao,
    private val birdDao: BirdDao,
    private val reptileDao: ReptileDao
) : ViewModel() {

    var name by mutableStateOf("")
    var birthday by mutableStateOf<Date?>(null)
    var gender by mutableStateOf(Gender.UNKNOWN)
    var animalType by mutableStateOf("None")

    // Bird fields
    var countryName by mutableStateOf("")
    var countryCode by mutableStateOf("")
    var departureDate by mutableStateOf<Date?>(null)
    var arrivalDate by mutableStateOf<Date?>(null)

    // Reptile fields
    var temperature by mutableStateOf("")
    var onsetOfHibernation by mutableStateOf<Date?>(null)
    var endOfHibernation by mutableStateOf<Date?>(null)

    fun saveAnimal(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var birdId: Long = -1
                var reptileId: Long = -1

                when (animalType) {
                    "Bird" -> {
                        val bird = Bird(
                            countryName = countryName,
                            countryCode = countryCode,
                            departureDate = departureDate ?: throw IllegalArgumentException("Departure date missing"),
                            arrivalDate = arrivalDate ?: throw IllegalArgumentException("Arrival date missing")
                        )
                        birdDao.insertBird(bird)
                        birdId = bird.birdId
                    }

                    "Reptile" -> {
                        val reptile = Reptile(
                            temperature = temperature.toFloatOrNull()
                                ?: throw IllegalArgumentException("Invalid temperature"),
                            onsetOfHibernation = onsetOfHibernation ?: throw IllegalArgumentException("Onset date missing"),
                            endOfHibernation = endOfHibernation ?: throw IllegalArgumentException("End date missing")
                        )
                        reptileDao.insertReptile(reptile)
                        reptileId = reptile.reptileId
                    }
                }

                val animal = Animal(
                    animalId = 0,
                    name = name,
                    dateOfBirthday = birthday ?: throw IllegalArgumentException("Birthday missing"),
                    gender = gender,
                    birdId = birdId,
                    reptileId = reptileId
                )

                animalDao.insertAnimal(animal)

                withContext(Dispatchers.Main) {
                    onSuccess()
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError(e.message ?: "Помилка при збереженні тварини")
                }
            }
        }
    }

}
