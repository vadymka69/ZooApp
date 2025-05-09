package com.vadickkt.zooapp.navigation

sealed class Screen(val route: String, val title: String) {
    data object Animals : Screen("animals", "Animals")
    data object Employees : Screen("employees", "Employees")
}
