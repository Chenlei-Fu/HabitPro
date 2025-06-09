package com.example.habittrackerpro.presentation.navigation

/**
 * Defines the routes for navigation within the app.
 */
sealed class NavigationRoute(val route: String) {
  object Home : NavigationRoute("home")
  object AddHabit : NavigationRoute("add_habit")
}
