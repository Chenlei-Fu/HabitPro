package com.example.habittrackerpro.presentation.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.habittrackerpro.domain.model.Habit

/**
 * A composable that displays a single habit item in a card.
 *
 * @param habit The habit to display.
 * @param onHabitClick Callback triggered when the habit card is clicked.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitItem(
  habit: Habit,
  onHabitClick: (Habit) -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier.fillMaxWidth(),
    onClick = { onHabitClick(habit) }
  ) {
    Row(
      modifier = Modifier.padding(16.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(text = habit.name, modifier = Modifier.weight(1f))
      Checkbox(checked = false, onCheckedChange = { onHabitClick(habit) })
    }
  }
}
