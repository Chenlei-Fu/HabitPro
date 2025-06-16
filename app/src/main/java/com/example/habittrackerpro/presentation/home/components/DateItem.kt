package com.example.habittrackerpro.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

/**
 * A composable that displays a single date item in the date selector.
 * It shows the day of the week and the day of the month.
 * Its appearance changes based on whether it is selected.
 *
 * @param date The ZonedDateTime to display.
 * @param isSelected Whether this date item is the currently selected one.
 * @param onClick A callback function that is invoked when this item is clicked.
 * @param modifier A modifier to be applied to this composable.
 */
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