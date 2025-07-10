package com.example.habittrackerpro.presentation.navigation

/**
 * Constants for the different navigation graphs in the app.
 */
object Graph {
  const val ROOT = "root_graph"
  const val AUTH = "auth_graph"
  const val MAIN = "main_graph"
}

/**
 * Defines the routes within the Authentication graph.
 */
sealed class AuthScreen(val route: String) {
  object Login : AuthScreen("login")
  object Signup : AuthScreen("signup")
}

/**
 * Defines the routes within the Main application graph.
 */
sealed class MainScreen(val route: String) {
  object Home : MainScreen("home")
  object AddHabit : MainScreen("add_habit")
  object Detail : MainScreen("detail/{habitId}") {
    fun createRoute(habitId: String) = "detail/$habitId"
    // Add the missing constant that DetailViewModel needs
    const val ARG_HABIT_ID = "habitId"
  }
  object Profile : MainScreen("profile") // <-- ADD THIS LINE
}

