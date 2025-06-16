package com.example.habittrackerpro.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import java.time.ZonedDateTime

@Composable
fun DateSelector(
  dates: List<ZonedDateTime>,
  selectedDate: ZonedDateTime,
  onDateClick: (ZonedDateTime) -> Unit
) {
  LazyRow(
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    contentPadding = PaddingValues(horizontal = 16.dp)
  ) {
    items(dates) { date ->
      DateItem(
        date = date,
        isSelected = date.toLocalDate() == selectedDate.toLocalDate(),
        onClick = onDateClick
      )
    }
  }
}