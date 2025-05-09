package com.vadickkt.zooapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName

@Entity("marriages")
data class Marriage(
    @PrimaryKey(autoGenerate = true)
    @SerialName("marriage_id")
    val marriageId: Int,
    @SerialName("partner_1_id")
    val partner1Id: Int,
    @SerialName("partner_2_id")
    val partner2Id: Int
)