package com.example.habittrackerpro.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.habittrackerpro.presentation.add_habit.AddHabitScreen
import com.example.habittrackerpro.presentation.detail.components.HabitDetailScreen
import com.example.habittrackerpro.presentation.home.HomeScreen


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
        onBack = { navController.popBackStack() }
      )
    }
  }
}

