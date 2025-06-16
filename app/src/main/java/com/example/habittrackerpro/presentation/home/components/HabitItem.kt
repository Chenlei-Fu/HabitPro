package com.example.habittrackerpro.presentation.home.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
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
import java.time.ZonedDateTime
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import java.time.format.DateTimeFormatter

@Composable
fun DateItem(
  date: ZonedDateTime,
  isSelected: Boolean,
  onClick: (ZonedDateTime) -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .clip(RoundedCornerShape(12.dp))
      .background(
        if (isSelected) Color(0xFF2C3E50) else Color.Transparent // Dark blue if selected
      )
      .clickable { onClick(date) }
      .padding(vertical = 12.dp, horizontal = 16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    val textColor = if (isSelected) Color.White else Color.Black
    // Display Day of Week (e.g., TUE)
    Text(
      text = date.format(DateTimeFormatter.ofPattern("E")),
      fontWeight = FontWeight.Bold,
      fontSize = 14.sp,
      color = textColor
    )
    // Display Day of Month (e.g., 18)
    Text(
      text = date.dayOfMonth.toString(),
      fontWeight = FontWeight.Bold,
      fontSize = 20.sp,
      color = textColor
    )
  }
}


/**
 * A composable that displays a single habit item in a card.
 *
 * @param habit The habit to display.
 * @param onHabitClick Callback triggered when the habit card is clicked.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HabitItem(
  habit: Habit,
  onHabitClick: (Habit) -> Unit,
  onCompletedClick: (Boolean) -> Unit,
  onHabitLongClick: (Habit) -> Unit,
  modifier: Modifier = Modifier
) {
  // Determine if the habit is completed for the current day
  val isCompletedToday = habit.completedDates.any {
    it.toLocalDate() == ZonedDateTime.now().toLocalDate()
  }

  Card(
    modifier = modifier
      .fillMaxWidth()
      .combinedClickable(
        onClick = { onHabitClick(habit) },
        onLongClick = { onHabitLongClick(habit) }
      )
  ) {
    Row(
      modifier = Modifier.padding(16.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(text = habit.name, modifier = Modifier.weight(1f))
      // The checkbox is now dynamic
      Checkbox(
        checked = isCompletedToday,
        onCheckedChange = onCompletedClick
      )
    }
  }
}
