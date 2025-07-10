package com.example.habittrackerpro.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.habittrackerpro.presentation.add_habit.AddHabitScreen
import com.example.habittrackerpro.presentation.detail.components.HabitDetailScreen
import com.example.habittrackerpro.presentation.home.HomeScreen
import com.example.habittrackerpro.presentation.profile.ProfileScreen


fun NavGraphBuilder.mainNavGraph(navController: NavHostController) {
  navigation(
    startDestination = MainScreen.Home.route,
    route = Graph.MAIN
  ) {
    composable(MainScreen.Home.route) {
      HomeScreen(
        onNewHabitClick = {
          navController.navigate(MainScreen.AddHabit.route)
        },
        onHabitClick = { habit ->
          navController.navigate(MainScreen.Detail.createRoute(habit.id))
        },
        // Pass the navigation callback for the settings icon
        onSettingsClick = {
          navController.navigate(MainScreen.Profile.route)
        }
      )
    }
    composable(MainScreen.AddHabit.route) {
      AddHabitScreen(
        onBack = { navController.popBackStack() }
      )
    }
    composable(MainScreen.Detail.route) {
      HabitDetailScreen(
        onBack = { navController.popBackStack() },
      )
    }
    composable(MainScreen.Profile.route) {
      ProfileScreen(
        onBack = { navController.popBackStack() },
        onLogout = {
          // Navigate back to the auth graph, clearing the entire back stack
          navController.navigate(Graph.AUTH) {
            popUpTo(Graph.ROOT) {
              inclusive = true
            }
          }
        }
      )
    }
  }
}

