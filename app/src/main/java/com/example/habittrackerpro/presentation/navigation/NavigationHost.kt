package com.example.habittrackerpro.presentation.navigation
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.habittrackerpro.presentation.add_habit.AddHabitScreen
import com.example.habittrackerpro.presentation.detail.components.HabitDetailScreen
import com.example.habittrackerpro.presentation.home.HomeScreen

@Composable
fun NavigationHost() {
  val navController = rememberNavController()
  NavHost(navController = navController, startDestination = NavigationRoute.Home.route) {
    composable(NavigationRoute.Home.route) {
      HomeScreen(
        onNewHabitClick = {
          navController.navigate(NavigationRoute.AddHabit.route)
        },
        // 将导航逻辑作为回调传递给HomeScreen
        onHabitClick = { habit ->
          navController.navigate(NavigationRoute.Detail.buildRoute(habit.id))
        }
      )
    }
    composable(NavigationRoute.AddHabit.route) {
      AddHabitScreen(
        onBack = { navController.popBackStack() }
      )
    }
    // 添加详情页的新目标
    composable(NavigationRoute.Detail.route) {
      HabitDetailScreen(
        onBack = { navController.popBackStack() }
      )
    }
  }
}

