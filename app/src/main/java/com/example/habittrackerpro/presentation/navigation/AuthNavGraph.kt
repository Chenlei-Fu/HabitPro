package com.example.habittrackerpro.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.habittrackerpro.presentation.auth.login.LoginScreen
import com.example.habittrackerpro.presentation.auth.signup.SignupScreen

fun NavGraphBuilder.authNavGraph(navController: NavController) {
  navigation(
    startDestination = AuthScreen.Login.route,
    route = Graph.AUTH
  ) {
    composable(route = AuthScreen.Login.route) {
      LoginScreen(
        onLoginSuccess = {
          navController.navigate(Graph.MAIN) {
            popUpTo(Graph.AUTH) { inclusive = true }
          }
        },
        onNavigateToSignup = {
          navController.navigate(AuthScreen.Signup.route)
        }
      )
    }
    composable(route = AuthScreen.Signup.route) {
      SignupScreen(
        onSignupSuccess = {
          navController.navigate(Graph.MAIN) {
            popUpTo(Graph.AUTH) { inclusive = true }
          }
        },
        onNavigateToLogin = {
          navController.popBackStack()
        }
      )
    }
  }
}
