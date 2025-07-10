package com.example.habittrackerpro.presentation.profile

import androidx.lifecycle.ViewModel
import com.example.habittrackerpro.domain.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
  private val repository: AuthRepository
) : ViewModel() {
  private val _state = MutableStateFlow(ProfileState())
  val state = _state.asStateFlow()

  init {
    // Get the current user's email when the ViewModel is created
    val currentUser = repository.currentUser
    _state.update {
      it.copy(
        email = currentUser?.email ?: "No email found"
      )
    }
  }

  fun onEvent(event: ProfileEvent) {
    when (event) {
      ProfileEvent.OnLogout -> {
        repository.signOut()
      }
    }
  }
}
