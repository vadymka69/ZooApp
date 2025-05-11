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
import com.vadickkt.zooapp.database.entities.Marriage
import com.vadickkt.zooapp.database.entities.Reptile

@Dao
interface ReptileDao {
    @Insert
    fun insertReptile(reptile: Reptile)

    @Delete
    fun deleteReptile(marriage: Marriage)

    @Query("SELECT * FROM marriages")
    fun getAllReptiles(): List<Marriage>

    @Query("SELECT * FROM reptiles WHERE reptileId = :id LIMIT 1")
    fun getReptileById(id: Long): Reptile?
}