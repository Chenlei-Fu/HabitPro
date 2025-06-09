package com.example.habittrackerpro.presentation.home
import com.example.habittrackerpro.domain.model.Habit
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
}
