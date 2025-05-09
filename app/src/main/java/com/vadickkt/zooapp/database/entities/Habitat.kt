package com.vadickkt.zooapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName

@Entity("habitats")
data class Habitat(
    @PrimaryKey(autoGenerate = true)
    @SerialName("habitat_id")
    val habitatId: String,
    val name: String,
    val description: String
)