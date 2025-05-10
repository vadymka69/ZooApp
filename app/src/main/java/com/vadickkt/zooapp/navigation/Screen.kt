package com.vadickkt.zooapp.navigation

sealed class Screen(val route: String, val title: String) {
    data object Home : Screen("home", "Home")
    data object Animals : Screen("animals", "Animals")
    data object Employees : Screen("employees", "Employees")
    data object AddAnimal : Screen("add_animal", "Add Animal")
}