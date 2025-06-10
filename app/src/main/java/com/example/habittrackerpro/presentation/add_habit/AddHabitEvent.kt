package com.example.habittrackerpro.presentation.add_habit

/**
 * Defines all possible user interactions on the Add Habit screen.
 */
sealed interface AddHabitEvent {
  /**
   * Triggered when the text in the habit name input field changes.
   * @param name The new text in the input field.
   */
  data class OnNameChange(val name: String) : AddHabitEvent

  /**
   * Triggered when the save button is clicked.
   */
  object OnSave : AddHabitEvent
}