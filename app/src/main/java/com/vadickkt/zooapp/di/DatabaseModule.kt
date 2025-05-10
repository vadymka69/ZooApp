package com.vadickkt.zooapp.di

import android.content.Context
import androidx.room.Room
import com.vadickkt.zooapp.database.ZooDatabase
import com.vadickkt.zooapp.database.dao.AnimalDao
import com.vadickkt.zooapp.database.dao.BirdDao
import com.vadickkt.zooapp.database.dao.DietDao
import com.vadickkt.zooapp.database.dao.EmployeeDao
import com.vadickkt.zooapp.database.dao.HabitatDao
import com.vadickkt.zooapp.database.dao.MarriageDao
import com.vadickkt.zooapp.database.dao.ReptileDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): ZooDatabase {
        return Room.databaseBuilder(
            context,
            ZooDatabase::class.java,
            "zoo_database"
        ).build()
    }

    @Provides
    fun provideAnimalDao(database: ZooDatabase): AnimalDao {
        return database.animalDao()
    }

    @Provides
    fun provideBirdDao(database: ZooDatabase): BirdDao {
        return database.birdDao()
    }

    @Provides
    fun provideDietDao(database: ZooDatabase): DietDao {
        return database.dietDao()
    }

    @Provides
    fun provideEmployeeDao(database: ZooDatabase): EmployeeDao {
        return database.employeeDao()
    }

    @Provides
    fun provideHabitatDao(database: ZooDatabase): HabitatDao {
        return database.habitatDao()
    }

    @Provides
    fun provideMarriageDao(database: ZooDatabase): MarriageDao {
        return database.marriageDao()
    }

    @Provides
    fun provideReptileDao(database: ZooDatabase): ReptileDao {
        return database.reptileDao()
    }
}