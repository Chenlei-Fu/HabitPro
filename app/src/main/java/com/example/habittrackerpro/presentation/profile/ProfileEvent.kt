package com.example.habittrackerpro.presentation.profile

/**
 * Defines all possible user interactions on the Profile screen.
 */
sealed interface ProfileEvent {
  /** Event triggered when the user clicks the "Logout" button. */
  object OnLogout : ProfileEvent
}