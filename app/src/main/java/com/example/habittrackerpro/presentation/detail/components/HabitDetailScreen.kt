package com.example.habittrackerpro.presentation.detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.habittrackerpro.presentation.detail.DetailEvent
import com.example.habittrackerpro.presentation.detail.DetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitDetailScreen(
  onBack: () -> Unit,
  viewModel: DetailViewModel = hiltViewModel()
) {
  val state by viewModel.state.collectAsStateWithLifecycle()
  val habit by viewModel.habitState.collectAsStateWithLifecycle()
  val displayedMonth by viewModel.displayedMonth.collectAsStateWithLifecycle()

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text(text = state.habit?.name ?: "Habit Detail") },
        navigationIcon = {
          IconButton(onClick = onBack) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
          }
        }
      )
    }
  ) { paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .padding(16.dp)
    ) {
      if (habit == null) {
        // 显示加载指示器
        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
      } else {
        // 显示习惯名称
        Text(
          text = habit!!.name,
          style = MaterialTheme.typography.headlineMedium,
          fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(24.dp))
        // 显示日历
        HabitDetailCalendar(
          completedDates = habit!!.completedDates.map { it.toLocalDate() },
          displayedMonth = displayedMonth,
          onPrevMonthClick = { viewModel.onEvent(DetailEvent.OnPrevMonthClick) },
          onNextMonthClick = { viewModel.onEvent(DetailEvent.OnNextMonthClick) }
        )
      }
    }
  }
}