package com.vadickkt.zooapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.vadickkt.zooapp.database.entities.Animal
import com.vadickkt.zooapp.database.entities.Bird

@Dao
interface BirdDao {
    @Insert
    fun insertBird(bird: Bird)

    @Delete
    fun deleteBird(bird: Bird)

    @Query("SELECT * FROM birds")
    fun getAllBirds(): List<Bird>

    @Query("SELECT * FROM birds WHERE birdId = :id LIMIT 1")
    fun getBirdById(id: Long): Bird?
}