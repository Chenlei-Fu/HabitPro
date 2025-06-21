package com.example.habittrackerpro.presentation.home.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.habittrackerpro.domain.model.Habit
import java.time.ZonedDateTime

/**
 * A composable that displays a single habit item in a card.
 *
 * @param habit The habit to display.
 * @param selectedDate The date currently selected on the HomeScreen.
 * @param onCardClick Callback triggered when the habit card is clicked.
 * @param onCheckboxClick Callback triggered when the checkbox state changes.
 * @param onHabitLongClick Callback triggered on a long press.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HabitItem(
  habit: Habit,
  selectedDate: ZonedDateTime,
  onCardClick: (Habit) -> Unit,
  onCheckboxClick: (Boolean) -> Unit,
  onHabitLongClick: (Habit) -> Unit,
  modifier: Modifier = Modifier
) {
  // 关键修改：检查习惯在 "selectedDate" 当天是否已完成
  val isCompletedForSelectedDate = habit.completedDates.any {
    // 使用传入的 selectedDate 进行比较，而不是 ZonedDateTime.now()
    it.toLocalDate() == selectedDate.toLocalDate()
  }

  Card(
    modifier = modifier.fillMaxWidth()
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .combinedClickable(
          onClick = { onCardClick(habit) },
          onLongClick = { onHabitLongClick(habit) }
        )
        .padding(16.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(text = habit.name, modifier = Modifier.weight(1f))
      Checkbox(
        // 使用我们新计算出的、正确的状态
        checked = isCompletedForSelectedDate,
        onCheckedChange = onCheckboxClick
      )
    }
  }
}