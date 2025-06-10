package com.example.habittrackerpro.presentation.add_habit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddHabitScreen(
  onBack: () -> Unit, // Callback to navigate back
  viewModel: AddHabitViewModel = hiltViewModel()
) {
  val state by viewModel.state.collectAsStateWithLifecycle()

  Scaffold { paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      TextField(
        value = state.habitName,
        onValueChange = { viewModel.onEvent(AddHabitEvent.OnNameChange(it)) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Enter habit name...") }
      )
      Button(
        onClick = {
          viewModel.onEvent(AddHabitEvent.OnSave)
          onBack() // Navigate back after saving
        },
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(text = "Save Habit")
      }
    }
  }
}