package com.vadickkt.zooapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.vadickkt.zooapp.converter.DateConverter
import kotlinx.serialization.SerialName
import java.util.Date

@Entity("employees")
@TypeConverters(DateConverter::class)
data class Employee(
    @PrimaryKey(autoGenerate = true)
    @SerialName("employee_id")
    val employeeId: Int,
    val name: String,
    @SerialName("date_of_birthday")
    val dateOfBirthday: Date,
    @SerialName("phone_number")
    val phoneNumber: String
)