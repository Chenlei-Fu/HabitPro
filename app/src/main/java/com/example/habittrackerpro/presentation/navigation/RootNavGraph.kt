package com.example.habittrackerpro.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun RootNavGraph() {
  val navController = rememberNavController()
  val startDestination = if (FirebaseAuth.getInstance().currentUser != null) {
    Graph.MAIN
  } else {
    Graph.AUTH
  }

  NavHost(
    navController = navController,
    route = Graph.ROOT,
    startDestination = startDestination
  ) {
    authNavGraph(navController = navController)
    mainNavGraph(navController = navController)
  }
}
