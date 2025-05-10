package com.vadickkt.zooapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.vadickkt.zooapp.database.entities.Animal
import com.vadickkt.zooapp.database.entities.Bird
import com.vadickkt.zooapp.database.entities.Diet
import com.vadickkt.zooapp.database.entities.Employee
import com.vadickkt.zooapp.database.entities.Habitat

@Dao
interface HabitatDao {
    @Insert
    fun insertHabitat(habitat: Habitat)

    @Delete
    fun deleteHabitat(habitat: Habitat)

    @Query("SELECT * FROM habitats")
    fun getAllHabitats(): List<Habitat>
}