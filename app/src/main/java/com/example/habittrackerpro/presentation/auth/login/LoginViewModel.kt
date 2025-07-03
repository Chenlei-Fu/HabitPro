package com.example.habittrackerpro.presentation.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittrackerpro.domain.auth.AuthRepository
import com.example.habittrackerpro.domain.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
  private val repository: AuthRepository
) : ViewModel() {
  private val _state = MutableStateFlow(LoginState())
  val state = _state.asStateFlow()

  fun onEvent(event: LoginEvent) {
    when (event) {
      is LoginEvent.EmailChanged -> _state.update { it.copy(email = event.email) }
      is LoginEvent.PasswordChanged -> _state.update { it.copy(pass = event.pass) }
      is LoginEvent.Login -> {
        viewModelScope.launch {
          _state.update { it.copy(loginResponse = Response.Loading) }
          val result = repository.signIn(state.value.email, state.value.pass)
          _state.update { it.copy(loginResponse = result) }
        }
      }
    }
  }
}