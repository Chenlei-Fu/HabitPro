package com.example.habittrackerpro.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittrackerpro.domain.model.Habit
import com.example.habittrackerpro.domain.repository.HabitRepository
import com.example.habittrackerpro.presentation.navigation.MainScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
  private val repository: HabitRepository,
  savedStateHandle: SavedStateHandle
) : ViewModel() {

  // The single source of truth for the Detail screen's UI state.
  private val _state = MutableStateFlow(DetailState())
  val state = _state.asStateFlow()

  init {
    val habitId = savedStateHandle.get<String>(MainScreen.Detail.ARG_HABIT_ID)
    if (habitId != null) {
      // Now we collect the Flow from the repository
      repository.getHabitById(habitId).onEach { entity ->
        entity?.let {
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
          // Update the habit within our single state object
          _state.update { s -> s.copy(habit = habit) }
        }
      }.launchIn(viewModelScope)
    }
  }

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
