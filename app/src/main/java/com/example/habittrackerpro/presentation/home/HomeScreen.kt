package com.example.habittrackerpro.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.habittrackerpro.domain.model.Habit
import com.example.habittrackerpro.presentation.home.components.DateSelector
import com.example.habittrackerpro.presentation.home.components.HabitItem

/**
 * The main screen of the application, displaying the list of habits.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
  onHabitClick: (Habit) -> Unit, // 接收导航回调
  onNewHabitClick: () -> Unit, // Callback for navigation
  onSettingsClick: () -> Unit, // Add a new callback for settings navigation
  viewModel: HomeViewModel = hiltViewModel()
) {
  val state by viewModel.state.collectAsStateWithLifecycle()
// Show AlertDialog if a habit is marked for deletion
  state.habitToDelete?.let { habit ->
    AlertDialog (
      onDismissRequest = { viewModel.onEvent(HomeEvent.OnDeleteHabitCancel) },
      title = { Text(text = "Delete Habit") },
      text = { Text(text = "Are you sure you want to delete '${habit.name}'?") },
      confirmButton = {
        Button(onClick = { viewModel.onEvent(HomeEvent.OnDeleteHabitConfirm) }) {
          Text("Confirm")
        }
      },
      dismissButton = {
        Button(onClick = { viewModel.onEvent(HomeEvent.OnDeleteHabitCancel) }) {
          Text("Cancel")
        }
      }
    )
  }
  Scaffold(
    modifier = Modifier.fillMaxSize(),
    topBar = {
      TopAppBar(
        title = { Text(text = "HabitTracker") },
        actions = {
          IconButton(onClick = onSettingsClick) {
            Icon(
              imageVector = Icons.Default.Settings,
              contentDescription = "Settings"
            )
          }
        }
      )
    },
    floatingActionButton = {
      FloatingActionButton(onClick = onNewHabitClick) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add Habit")
      }
    }
  ) { paddingValues ->
    LazyColumn(
      modifier = Modifier.padding(paddingValues),
      contentPadding = PaddingValues(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      item {
        // Replace the static text with our dynamic DateSelector
        DateSelector (
          dates = state.visibleDates,
          selectedDate = state.selectedDate,
          onDateClick = {
            viewModel.onEvent(HomeEvent.OnDateClick(it))
          }
        )
      }
      items(state.habits) { habit ->
        HabitItem(
          habit = habit,
          selectedDate = state.selectedDate,
          onCardClick = { onHabitClick(habit) },
          onCheckboxClick = { isCompleted ->
            viewModel.onEvent(HomeEvent.OnCompletedClick(habit, isCompleted))
          },
          onHabitLongClick = { viewModel.onEvent(HomeEvent.OnHabitLongClick(habit)) }
        )
      }
    }
  }
}
