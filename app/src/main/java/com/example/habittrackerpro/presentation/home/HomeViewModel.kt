package com.example.habittrackerpro.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittrackerpro.domain.model.Habit
import com.example.habittrackerpro.domain.repository.HabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * The ViewModel for the Home screen. It holds the UI state and handles business logic.
 *
 * @param repository The repository for accessing habit data. Hilt provides this.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
  private val repository: HabitRepository
) : ViewModel() {

  private val _state = MutableStateFlow(HomeState())
  val state = _state.asStateFlow()

  init {
    // Start observing the habits from the repository as soon as the ViewModel is created.
    repository.getAllHabits().onEach { habits ->
      _state.update {
        // We need to map from HabitEntity to our domain Habit model
        // For now, we'll create a simple mapping.
        // A proper mapper class would be better in a larger project.
        val domainHabits = habits.map { Habit(id = it.id, name = it.name) }
        it.copy(
          habits = domainHabits
        )
      }
    }.launchIn(viewModelScope)
  }

  fun onEvent(event: HomeEvent) {
    when (event) {
      is HomeEvent.OnAddHabitClick -> {
        // Logic to navigate will be handled by the UI layer
      }
      is HomeEvent.OnHabitClick -> {
        // Logic to mark a habit as completed will be added later
      }
      is HomeEvent.OnHabitLongClick -> {
        // Logic for deleting a habit will be added later
      }
    }
  }
}
