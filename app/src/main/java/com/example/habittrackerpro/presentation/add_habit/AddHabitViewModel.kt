package com.example.habittrackerpro.presentation.add_habit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittrackerpro.data.local.entity.HabitEntity
import com.example.habittrackerpro.domain.repository.HabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddHabitViewModel @Inject constructor(
  private val repository: HabitRepository
) : ViewModel() {
  private val _state = MutableStateFlow(AddHabitState())
  val state = _state.asStateFlow()

  fun onEvent(event: AddHabitEvent) {
    when (event) {
      is AddHabitEvent.OnNameChange -> {
        _state.update { it.copy(habitName = event.name) }
      }
      AddHabitEvent.OnSave -> {
        viewModelScope.launch {
          val habit = HabitEntity(
            id = UUID.randomUUID().toString(),
            name = state.value.habitName,
            frequency = emptyList(), // For now, we keep these simple
            completedDates = emptyList(),
            reminder = 0L,
            startDate = ZonedDateTime.now().toEpochSecond()
          )
          repository.insertHabit(habit)
        }
      }
    }
  }
}
