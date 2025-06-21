package com.example.habittrackerpro.presentation.detail

/**
 * Defines all possible user interactions (events) on the Detail screen.
 */
sealed interface DetailEvent {
  /** Event triggered when the user clicks the "previous month" button on the calendar. */
  object OnPrevMonthClick : DetailEvent

  /** Event triggered when the user clicks the "next month" button on the calendar. */
  object OnNextMonthClick : DetailEvent
}
