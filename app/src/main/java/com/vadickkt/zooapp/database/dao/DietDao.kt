package com.vadickkt.zooapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.vadickkt.zooapp.database.entities.Animal
import com.vadickkt.zooapp.database.entities.Bird
import com.vadickkt.zooapp.database.entities.Diet

@Dao
interface DietDao {
    @Insert
    fun insertDiet(diet: Diet)

    @Delete
    fun deleteDiet(diet: Diet)

    @Query("SELECT * FROM diets")
    fun getAllDiets(): List<Diet>
}