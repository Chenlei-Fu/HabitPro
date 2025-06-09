package com.example.habittrackerpro.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.habittrackerpro.presentation.home.HomeScreen
import com.example.habittrackerpro.presentation.add_habit.AddHabitScreen

/**
 * The navigation host for the application. It defines the navigation graph.
 */
@Composable
fun NavigationHost() {
  val navController = rememberNavController()
  NavHost(navController = navController, startDestination = NavigationRoute.Home.route) {
    composable(NavigationRoute.Home.route) {
      HomeScreen(
        onNewHabitClick = {
          // When the FAB is clicked, navigate to the AddHabit screen
          navController.navigate(NavigationRoute.AddHabit.route)
        }
      )
    }
    // Define the destination for the "add_habit" route
    composable(NavigationRoute.AddHabit.route) {
      AddHabitScreen(
        onBack = {
          // Navigate back to the previous screen (Home)
          navController.popBackStack()
        }
      )
    }
  }
}