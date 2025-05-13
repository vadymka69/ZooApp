package com.vadickkt.zooapp.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Animal : Screen("animal")
    object Employee : Screen("employee")
    object AnimalDetails : Screen("animal_details/{animalId}") {
        fun createRoute(animalId: Long) = "animal_details/$animalId"
    }
    object AddNewAnimal : Screen("add_animal")
    object AddNewEmployee : Screen("add_new_employee")
    object Rations : Screen("rations")
    object SelectEmployee : Screen("select_employee/{type}") {
        fun createRoute(type: String) = "select_employee/$type"
    }
    object Marriage : Screen("marriage")
}