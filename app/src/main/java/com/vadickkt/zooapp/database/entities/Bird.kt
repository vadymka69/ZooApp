package com.vadickkt.zooapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.vadickkt.zooapp.converter.DateConverter
import kotlinx.serialization.SerialName
import java.util.Date

@Entity("birds")
@TypeConverters(DateConverter::class)
data class Bird(
    @PrimaryKey(autoGenerate = true)
    @SerialName("bird_id")
    val birdId: Long,
    @SerialName("country_name")
    val countryName: String,
    @SerialName("country_code")
    val countryCode: String,
    @SerialName("departure_date")
    val departureDate: Date,
    @SerialName("arrival_date")
    val arrivalDate: Date,
)