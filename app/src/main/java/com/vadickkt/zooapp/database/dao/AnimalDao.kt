package com.vadickkt.zooapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.vadickkt.zooapp.database.entities.Animal

@Dao
interface AnimalDao {
    @Insert
    fun insertAnimal(animal: Animal)

    @Delete
    fun deleteAnimal(animal: Animal)

    @Query("SELECT * FROM animals")
    fun getAllAnimals(): List<Animal>

    @Query("SELECT * FROM animals WHERE animalId = :id LIMIT 1")
    fun getAnimalById(id: Long): Animal?
}