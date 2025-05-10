package com.vadickkt.zooapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.vadickkt.zooapp.ui.screen.AddAnimalScreen
import com.vadickkt.zooapp.ui.screen.HomeScreen

@Composable
fun Navigation(navigationController: NavHostController) {
    NavHost(
        navController = navigationController,
        startDestination = Screen.Animals.route,
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navigationController)
        }

        composable(Screen.AddAnimal.route) {
            AddAnimalScreen()
        }
    }
}