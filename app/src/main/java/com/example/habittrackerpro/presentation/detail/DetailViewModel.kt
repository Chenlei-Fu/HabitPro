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
import kotlinx.coroutines.flow.*
import java.time.YearMonth

@HiltViewModel
class DetailViewModel @Inject constructor(
  private val repository: HabitRepository,
  savedStateHandle: SavedStateHandle // Injected by Hilt to access navigation arguments
) : ViewModel() {

  private val _state = MutableStateFlow(DetailState())
  val state = _state.asStateFlow()

  private val _displayedMonth = MutableStateFlow(YearMonth.now())
  val displayedMonth = _displayedMonth.asStateFlow()

  private val habitId: String = checkNotNull(savedStateHandle[NavigationRoute.Detail.ARG_HABIT_ID])

  // 创建一个能实时响应数据变化的 habit StateFlow
  val habitState: StateFlow<Habit?> = repository.getHabitById(habitId)
    .map { entity ->
      // 当 entity 为 null 时，也进行映射，结果为 null
      entity?.let {
        Habit(
          id = it.id,
          name = it.name,
          completedDates = it.completedDates.map { timestamp ->
            ZonedDateTime.ofInstant(
              java.time.Instant.ofEpochSecond(timestamp),
              java.time.ZoneId.systemDefault()
            )
          }
        )
      }
    }
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5000),
      initialValue = null // 初始值为 null，表示正在加载或无数据
    )

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