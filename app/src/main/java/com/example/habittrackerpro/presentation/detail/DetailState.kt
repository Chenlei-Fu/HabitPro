package com.example.habittrackerpro.presentation.detail

import com.example.habittrackerpro.domain.model.Habit
import java.time.YearMonth
/**
 * Represents the state for the Habit Detail screen.
 *
 * @property habit The habit being displayed. Can be null initially while loading.
 */
data class DetailState(
  val habit: Habit? = null,
  val displayedMonth: YearMonth = YearMonth.now()
)