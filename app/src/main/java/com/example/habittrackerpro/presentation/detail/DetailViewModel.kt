package com.example.habittrackerpro.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittrackerpro.domain.model.Habit
import com.example.habittrackerpro.domain.repository.HabitRepository
import com.example.habittrackerpro.presentation.navigation.NavigationRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
  private val repository: HabitRepository,
  savedStateHandle: SavedStateHandle // Injected by Hilt to access navigation arguments
) : ViewModel() {

  private val _state = MutableStateFlow(DetailState())
  val state = _state.asStateFlow()

  init {
    // Retrieve the habitId argument from the navigation route
    val habitId = savedStateHandle.get<String>(NavigationRoute.Detail.ARG_HABIT_ID)
    if (habitId != null) {
      viewModelScope.launch {
        val entity = repository.getHabitById(habitId)
        entity?.let {
          // Map the entity to the domain model for the UI
          val habit = Habit(
            id = it.id,
            name = it.name,
            completedDates = it.completedDates.map { timestamp ->
              ZonedDateTime.ofInstant(
                java.time.Instant.ofEpochSecond(timestamp),
                java.time.ZoneId.systemDefault()
              )
            }
          )
          _state.update { s -> s.copy(habit = habit) }
        }
      }
    }
  }


  /**
   * Handles events sent from the UI.
   * @param event The event to be processed.
   */
  fun onEvent(event: DetailEvent) {
    when (event) {
      DetailEvent.OnPrevMonthClick -> {
        _state.update {
          it.copy(
            displayedMonth = it.displayedMonth.minusMonths(1)
          )
        }
      }
      DetailEvent.OnNextMonthClick -> {
        _state.update {
          it.copy(
            displayedMonth = it.displayedMonth.plusMonths(1)
          )
        }
      }
    }
  }
}