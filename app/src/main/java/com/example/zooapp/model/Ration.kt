package com.example.zooapp.model

data class Ration(
    val id: Long = 0,
    val name: String,
    val type: RationType,
    val description: String = ""
)

enum class RationType {
    CHILD,      // Дитячий
    DIET,       // Дієтичний
    ENHANCED,   // Посилений
    STANDARD    // Стандартний
} 