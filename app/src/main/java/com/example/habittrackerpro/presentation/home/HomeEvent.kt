package com.example.habittrackerpro.presentation.home
import com.example.habittrackerpro.domain.model.Habit
import java.time.ZonedDateTime

/**
 * Represents all possible user interactions (events) on the Home screen.
 * Using a sealed interface ensures that we can handle all possible events in a when block.
 */
sealed interface HomeEvent {
  /**
   * Event triggered when a habit is checked or unchecked.
   * @param habit The habit that was interacted with.
   */
  data class OnHabitClick(val habit: Habit) : HomeEvent

  /** Event triggered when the "add habit" button is clicked. */
  object OnAddHabitClick : HomeEvent

  /**
   * Event triggered when a habit is long-pressed, usually to show options like deletion.
   * @param habit The habit that was long-pressed.
   */
  data class OnHabitLongClick(val habit: Habit) : HomeEvent

  /**
   * Event triggered when the completion checkbox of a habit is clicked.
   * This event carries the necessary data for the ViewModel to process the update.
   *
   * @param habit The habit whose completion status is being changed.
   * @param isCompleted The new completion state (true if checked, false if unchecked).
   */
  data class OnCompletedClick(val habit: Habit, val isCompleted: Boolean) : HomeEvent

  // Triggered when the user confirms the deletion in the dialog
  object OnDeleteHabitConfirm : HomeEvent

  // Triggered when the user cancels the deletion dialog
  object OnDeleteHabitCancel : HomeEvent

  // Event triggered when a user clicks on a date in the date selector.
  data class OnDateClick(val date: ZonedDateTime) : HomeEvent
}
