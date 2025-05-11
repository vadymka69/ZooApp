package com.vadickkt.zooapp.database.entities

import android.os.Parcelable
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
    val dietId: Long = 0,
    val name: String,
    val type: DietType,
    val description: String = ""
)

enum class DietType {
    CHILD,      // Дитячий
    DIET,       // Дієтичний
    ENHANCED,   // Посилений
    STANDARD    // Стандартний
}
