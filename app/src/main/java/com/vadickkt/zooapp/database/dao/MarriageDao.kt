package com.vadickkt.zooapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vadickkt.zooapp.database.entities.Marriage

@Dao
interface MarriageDao {
    @Query("SELECT * FROM marriages")
    suspend fun getAllMarriages(): List<Marriage>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarriage(marriage: Marriage)

    @Delete
    suspend fun deleteMarriage(marriage: Marriage)
}