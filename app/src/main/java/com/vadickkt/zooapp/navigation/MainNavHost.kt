package com.vadickkt.zooapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.vadickkt.zooapp.ui.screen.AddAnimalScreen
import com.vadickkt.zooapp.ui.screen.AddNewEmployeeScreen
import com.vadickkt.zooapp.ui.screen.AnimalDetailsScreen
import com.vadickkt.zooapp.ui.screen.HomeScreen
import com.vadickkt.zooapp.ui.screen.RationsScreen

@Composable
fun MainNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }

        composable(Screen.AddNewAnimal.route) {
            AddAnimalScreen()
        }

        composable(
            route = Screen.AnimalDetails.route,
            arguments = listOf(
                navArgument("animalId") { type = androidx.navigation.NavType.LongType }
            )
        ) { backStackEntry ->
            val animalId = backStackEntry.arguments?.getLong("animalId") ?: -1L
            AnimalDetailsScreen(
                animalId = animalId,
                navController = navController
            )
        }

        composable(Screen.AddNewEmployee.route) {
            AddNewEmployeeScreen()
        }

        composable(Screen.Rations.route) {
            RationsScreen(
                onRationSelected = { diet ->
                    // Передаємо тільки ID раціону
                    navController.previousBackStackEntry?.savedStateHandle?.set("selected_diet_id", diet.dietId)
                    navController.popBackStack()
                }
            )
        }
    }
}
