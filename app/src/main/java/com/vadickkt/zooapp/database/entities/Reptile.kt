package com.vadickkt.zooapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.vadickkt.zooapp.converter.DateConverter
import kotlinx.serialization.SerialName
import java.util.Date

@Entity("reptiles")
@TypeConverters(DateConverter::class)
data class Reptile(
    @PrimaryKey(autoGenerate = true)
    @SerialName("reptile_id")
    val reptileId: Long,
    val temperature: Float,
    @SerialName("onset_of_hibernation")
    val onsetOfHibernation: Date,
    @SerialName("end_of_hibernation")
    val endOfHibernation: Date,
)