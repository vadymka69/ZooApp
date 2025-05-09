package com.vadickkt.zooapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.vadickkt.zooapp.converter.DateConverter
import kotlinx.serialization.SerialName

@Entity("diets")
@TypeConverters(DateConverter::class)
data class Diet(
    @PrimaryKey(autoGenerate = true)
    @SerialName("diet_id")
    val dietId: Long,
    val name: String,
    @SerialName("diet_type")
    val dietType: DietType
)

enum class DietType { CHILD, DIETARY, ENHANCED }
