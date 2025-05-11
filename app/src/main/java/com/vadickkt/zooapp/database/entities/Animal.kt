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
    val animalId: Long = 0,
    val name: String,
    val gender: Gender,
    @SerialName("date_of_birthday")
    val dateOfBirthday: Date,
    @SerialName("bird_id")
    val birdId: Long = -1,
    @SerialName("reptile_id")
    val reptileId: Long = -1,
    @SerialName("diet_id")
    val dietId: Long = -1,
    @SerialName("vet_id")
    val vetId: Long = -1,
    @SerialName("caretaker_id")
    val caretakerId: Long = -1
) {
    constructor(
        animalId: Long,
        name: String,
        dateOfBirthday: Date,
        gender: Gender,
        birdId: Long,
        reptileId: Long,
        dietId: Long
    ) : this(
        animalId = animalId,
        name = name,
        gender = gender,
        dateOfBirthday = dateOfBirthday,
        birdId = birdId,
        reptileId = reptileId,
        dietId = dietId,
        vetId = -1,
        caretakerId = -1
    )
}

enum class Gender { MALE, FEMALE, UNKNOWN }