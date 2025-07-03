package com.example.habittrackerpro.presentation.auth.login

sealed interface LoginEvent {
  data class EmailChanged(val email: String) : LoginEvent
  data class PasswordChanged(val pass: String) : LoginEvent
  object Login : LoginEvent
}