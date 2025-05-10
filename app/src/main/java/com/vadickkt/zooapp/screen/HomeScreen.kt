package com.vadickkt.zooapp.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vadickkt.zooapp.navigation.Screen

@Composable
fun HomeScreen(
    navigationController: NavHostController
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navigationController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navigationController,
            startDestination = Screen.Animals.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Animals.route) {
                AnimalsScreen()
            }
            composable(Screen.Employees.route) {
                EmployeesScreen()
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        Screen.Animals,
        Screen.Employees,
    )

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = when (screen) {
                            Screen.Animals -> Icons.Default.Done
                            Screen.Employees -> Icons.Default.Favorite
                            else -> Icons.Default.Home
                        },
                        contentDescription = screen.title
                    )
                },
                label = { Text(screen.title) }
            )
        }
    }
}
