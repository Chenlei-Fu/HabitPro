package com.example.habittrackerpro.presentation.detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.habittrackerpro.presentation.detail.DetailEvent
import com.example.habittrackerpro.presentation.detail.DetailViewModel
import com.example.habittrackerpro.presentation.detail.components.HabitDetailCalendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitDetailScreen(
  onBack: () -> Unit,
  viewModel: DetailViewModel = hiltViewModel()
) {
  // Now we only collect the single state object
  val state by viewModel.state.collectAsStateWithLifecycle()

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text(text = state.habit?.name ?: "Habit Detail") },
        navigationIcon = {
          IconButton(onClick = onBack) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
          }
        }
      )
    }
  ) { paddingValues ->
    // Use the habit from the state object. It can be null while loading.
    state.habit?.let { habit ->
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(paddingValues)
          .padding(16.dp)
      ) {
        HabitDetailCalendar(
          completedDates = habit.completedDates.map { it.toLocalDate() },
          // Pass the displayedMonth from the single state object
          displayedMonth = state.displayedMonth,
          onPrevMonthClick = { viewModel.onEvent(DetailEvent.OnPrevMonthClick) },
          onNextMonthClick = { viewModel.onEvent(DetailEvent.OnNextMonthClick) }
        )
      }
    }
  }
}
