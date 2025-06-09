package com.example.habittrackerpro.presentation.home
import com.example.habittrackerpro.domain.model.Habit
import java.time.ZonedDateTime

/**
 * Represents all the necessary information to render the Home screen.
 *
 * @property currentDate The current date being displayed.
 * @property habits The list of habits to be displayed on the screen.
 */
data class HomeState(
  val currentDate: ZonedDateTime = ZonedDateTime.now(),
  val habits: List<Habit> = emptyList()
)
