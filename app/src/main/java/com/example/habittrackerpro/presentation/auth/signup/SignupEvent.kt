package com.example.habittrackerpro.presentation.auth.signup

sealed interface SignupEvent {
  data class EmailChanged(val email: String) : SignupEvent
  data class PasswordChanged(val pass: String) : SignupEvent
  object Signup : SignupEvent
}
