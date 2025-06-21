package com.example.habittrackerpro.presentation.home
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittrackerpro.data.local.entity.HabitEntity
import com.example.habittrackerpro.domain.model.Habit
import com.example.habittrackerpro.domain.repository.HabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val repository: HabitRepository
) : ViewModel() {

  private val _state = MutableStateFlow(HomeState())
  val state = _state.asStateFlow()

  init {
    repository.getAllHabits().onEach { entityList ->
      _state.update {
        // When mapping from Entity to Domain model, include completedDates
        val domainHabits = entityList.map { entity ->
          Habit(
            id = entity.id,
            name = entity.name,
            completedDates = entity.completedDates.map { timestamp ->
              // Assuming timestamps are stored as Long (epoch seconds)
              ZonedDateTime.ofInstant(
                java.time.Instant.ofEpochSecond(timestamp),
                java.time.ZoneId.systemDefault()
              )
            }
          )
        }
        it.copy(
          habits = domainHabits
        )
      }
    }.launchIn(viewModelScope)

    // Initialize the visible dates when the ViewModel is created
    updateVisibleDates()
  }

  fun onEvent(event: HomeEvent) {
    when (event) {
      // ... (OnAddHabitClick, OnHabitClick, OnHabitLongClick remain the same) ...
      is HomeEvent.OnAddHabitClick -> {}
      is HomeEvent.OnHabitClick -> {}
      // Handle long-click: set the habit to be deleted to show the dialog
      is HomeEvent.OnHabitLongClick -> {
        _state.update { it.copy(habitToDelete = event.habit) }
      }

      is HomeEvent.OnCompletedClick -> {
        viewModelScope.launch {
          val dateToModify = state.value.selectedDate

          // 从 Flow 中获取第一个非 null 的值来进行更新
          val currentEntity = repository.getHabitById(event.habit.id).filterNotNull().first()

          val completedTimestamps = currentEntity.completedDates.toMutableList()

          if (event.isCompleted) {
            val alreadyCompleted = completedTimestamps.any { timestamp ->
              ZonedDateTime.ofInstant(java.time.Instant.ofEpochSecond(timestamp), java.time.ZoneId.systemDefault())
                .toLocalDate() == dateToModify.toLocalDate()
            }
            if (!alreadyCompleted) {
              completedTimestamps.add(dateToModify.toEpochSecond())
            }
          } else {
            completedTimestamps.removeAll { timestamp ->
              ZonedDateTime.ofInstant(java.time.Instant.ofEpochSecond(timestamp), java.time.ZoneId.systemDefault())
                .toLocalDate() == dateToModify.toLocalDate()
            }
          }

          val habitToUpdate = currentEntity.copy(completedDates = completedTimestamps)

          repository.updateHabit(habitToUpdate)
        }
      }
      // Handle deletion confirmation
      is HomeEvent.OnDeleteHabitConfirm -> {
        viewModelScope.launch {
          state.value.habitToDelete?.let { habit ->
            val entityToDelete = HabitEntity(
              id = habit.id,
              name = habit.name,
              completedDates = habit.completedDates.map { it.toEpochSecond() },
              frequency = emptyList(), // These fields need to be present for the entity
              reminder = 0L,
              startDate = 0L
            )
            repository.deleteHabit(entityToDelete)
          }
          // Hide the dialog after deletion
          _state.update { it.copy(habitToDelete = null) }
        }
      }

      // Handle deletion cancellation
      is HomeEvent.OnDeleteHabitCancel -> {
        _state.update { it.copy(habitToDelete = null) }
      }

      // Handle date selection event
      is HomeEvent.OnDateClick -> {
        _state.update { it.copy(selectedDate = event.date) }
        // After selecting a new date, we might want to regenerate the visible dates
        // For now, we'll keep it simple. A more advanced implementation
        // could slide the dates.
        updateVisibleDates(event.date)
      }
    }
  }

  // A helper function to generate the list of dates to display
  private fun updateVisibleDates(newSelectedDate: ZonedDateTime = _state.value.selectedDate) {
    val dates = mutableListOf<ZonedDateTime>()
    // Add 3 days before the selected date
    for (i in 3 downTo 1) {
      dates.add(newSelectedDate.minusDays(i.toLong()))
    }
    // Add the selected date
    dates.add(newSelectedDate)
    // Add 3 days after the selected date
    for (i in 1..3) {
      dates.add(newSelectedDate.plusDays(i.toLong()))
    }
    _state.update { it.copy(visibleDates = dates) }
  }
}