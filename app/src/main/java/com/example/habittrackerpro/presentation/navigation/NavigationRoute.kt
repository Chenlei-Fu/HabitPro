package com.example.habittrackerpro.presentation.navigation

/**
 * Defines the routes for navigation within the app.
 */
sealed class NavigationRoute(val route: String) {
  object Home : NavigationRoute("home")
  object AddHabit : NavigationRoute("add_habit")
  // Define the detail route with an argument for the habit ID
  object Detail : NavigationRoute("detail/{habitId}") {
    // A helper function to create the route with a specific ID
    fun buildRoute(habitId: String) = "detail/$habitId"
    // Constant for the argument key
    const val ARG_HABIT_ID = "habitId"
  }
}
