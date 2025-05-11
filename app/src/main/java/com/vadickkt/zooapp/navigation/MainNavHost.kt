package com.vadickkt.zooapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.vadickkt.zooapp.ui.screen.AddAnimalScreen
import com.vadickkt.zooapp.ui.screen.HomeScreen

@Composable
fun MainNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }

        composable(Screen.AddNewAnimal.route) {
            AddAnimalScreen()
        }
    }
}
