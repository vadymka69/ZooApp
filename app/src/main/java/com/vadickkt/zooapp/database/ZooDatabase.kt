package com.vadickkt.zooapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vadickkt.zooapp.converter.DateConverter
import com.vadickkt.zooapp.database.dao.AnimalDao
import com.vadickkt.zooapp.database.dao.BirdDao
import com.vadickkt.zooapp.database.dao.DietDao
import com.vadickkt.zooapp.database.dao.EmployeeDao
import com.vadickkt.zooapp.database.dao.HabitatDao
import com.vadickkt.zooapp.database.dao.MarriageDao
import com.vadickkt.zooapp.database.dao.ReptileDao
import com.vadickkt.zooapp.database.entities.Animal
import com.vadickkt.zooapp.database.entities.Bird
import com.vadickkt.zooapp.database.entities.Diet
import com.vadickkt.zooapp.database.entities.Employee
import com.vadickkt.zooapp.database.entities.Habitat
import com.vadickkt.zooapp.database.entities.Marriage
import com.vadickkt.zooapp.database.entities.Reptile

@Database(
    entities = [Animal::class, Bird::class, Diet::class, Employee::class, Habitat::class, Marriage::class, Reptile::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class ZooDatabase : RoomDatabase() {

    abstract fun animalDao(): AnimalDao
    abstract fun birdDao(): BirdDao
    abstract fun dietDao(): DietDao
    abstract fun employeeDao(): EmployeeDao
    abstract fun habitatDao(): HabitatDao
    abstract fun marriageDao(): MarriageDao
    abstract fun reptileDao(): ReptileDao

    companion object {
        @Volatile
        private var INSTANCE: ZooDatabase? = null

        fun getDatabase(context: Context): ZooDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ZooDatabase::class.java,
                    "zoo_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}