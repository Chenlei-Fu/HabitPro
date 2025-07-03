package com.example.habittrackerpro.presentation.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittrackerpro.domain.auth.AuthRepository
import com.example.habittrackerpro.domain.model.Response
import com.example.habittrackerpro.presentation.auth.signup.SignupState
import com.example.habittrackerpro.presentation.auth.signup.SignupEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
  private val repository: AuthRepository
) : ViewModel() {
  private val _state = MutableStateFlow(SignupState())
  val state = _state.asStateFlow()

  fun onEvent(event: SignupEvent) {
    when (event) {
      is SignupEvent.EmailChanged -> _state.update { it.copy(email = event.email) }
      is SignupEvent.PasswordChanged -> _state.update { it.copy(pass = event.pass) }
      is SignupEvent.Signup -> {
        viewModelScope.launch {
          _state.update { it.copy(signupResponse = Response.Loading) }
          val result = repository.signUp(state.value.email, state.value.pass)
          _state.update { it.copy(signupResponse = result) }
        }
      }
    }
  }
}
