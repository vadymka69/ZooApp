package com.vadickkt.zooapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.vadickkt.zooapp.converter.DateConverter
import kotlinx.serialization.SerialName
import java.util.Date

@Entity("animals")
@TypeConverters(DateConverter::class)
data class Animal(
    @PrimaryKey(autoGenerate = true)
    @SerialName("animal_id")
    val animalId: Long,
    val name: String,
    @SerialName("date_of_birthday")
    val dateOfBirthday: Date,
    val gender: Gender,
    @SerialName("bird_id")
    val birdId: Long = -1,
    @SerialName("reptile_id")
    val reptileId: Long = -1,
    @SerialName("diet_id")
    val dietId: Long = -1
)

enum class Gender { MALE, FEMALE, UNKNOWN }