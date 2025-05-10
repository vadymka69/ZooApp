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

@Dao
interface MarriageDao {
    @Insert
    fun insertMarriage(marriage: Marriage)

    @Delete
    fun deleteMarriage(marriage: Marriage)

    @Query("SELECT * FROM marriages")
    fun getAllMarriages(): List<Marriage>
}