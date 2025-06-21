package com.example.habittrackerpro.presentation.detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

/**
 * A fully interactive calendar view for the habit detail screen.
 * Displays a monthly grid and allows navigation between months.
 *
 * @param completedDates A list of dates on which the habit was completed.
 * @param displayedMonth The month currently being displayed by the calendar.
 * @param onPrevMonthClick Callback invoked when the "previous month" button is clicked.
 * @param onNextMonthClick Callback invoked when the "next month" button is clicked.
 * @param modifier A modifier to be applied to this composable.
 */
@Composable
fun HabitDetailCalendar(
  completedDates: List<LocalDate>,
  displayedMonth: YearMonth,
  onPrevMonthClick: () -> Unit,
  onNextMonthClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val firstDayOfMonth = displayedMonth.atDay(1)
  val lastDayOfMonth = displayedMonth.atEndOfMonth()
  val daysInMonth = (1..lastDayOfMonth.dayOfMonth).toList()

  val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7

  Column(modifier = modifier) {
    // Calendar Header: Month, Year, and Navigation Buttons
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      IconButton(onClick = onPrevMonthClick) {
        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Previous Month")
      }
      Text(
        text = displayedMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")),
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.titleLarge
      )
      IconButton(onClick = onNextMonthClick) {
        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next Month")
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Days of the week header
    Row(modifier = Modifier.fillMaxWidth()) {
      val weekDays = listOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT")
      weekDays.forEach { day ->
        Text(
          text = day,
          modifier = Modifier.weight(1f),
          textAlign = TextAlign.Center,
          fontWeight = FontWeight.SemiBold,
          style = MaterialTheme.typography.bodySmall
        )
      }
    }

    Spacer(modifier = Modifier.height(8.dp))

    // Calendar grid
    for (i in 0 until 6) {
      Row(modifier = Modifier.fillMaxWidth()) {
        for (j in 0 until 7) {
          val dayIndex = i * 7 + j
          val day = dayIndex - firstDayOfWeek + 1

          Box(modifier = Modifier.weight(1f).aspectRatio(1f), contentAlignment = Alignment.Center) {
            if (day in daysInMonth) {
              val date = displayedMonth.atDay(day)
              val isCompleted = completedDates.contains(date)

              Text(
                text = day.toString(),
                modifier = Modifier
                  .clip(CircleShape)
                  .background(if (isCompleted) MaterialTheme.colorScheme.primary else Color.Transparent)
                  .padding(4.dp),
                textAlign = TextAlign.Center,
                color = if (isCompleted) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onBackground
              )
            }
          }
        }
      }
    }
  }
}
