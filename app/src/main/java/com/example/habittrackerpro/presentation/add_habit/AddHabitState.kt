package com.example.habittrackerpro.presentation.add_habit

/**
 * Represents the state for the Add Habit screen.
 *
 * @property habitName The current text entered by the user for the habit's name.
 */
data class AddHabitState(
  val habitName: String = ""
)