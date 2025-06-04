package com.example.habittrackerpro.data.repository

import com.example.habittrackerpro.data.local.HabitDao
import com.example.habittrackerpro.data.local.entity.HabitEntity
import com.example.habittrackerpro.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Concrete implementation of the HabitRepository interface.
 * This class is responsible for coordinating data operations from the local data source (DAO).
 *
 * @param habitDao The Data Access Object for habits. Hilt will provide this dependency.
 */
class HabitRepositoryImpl @Inject constructor(
  private val habitDao: HabitDao
) : HabitRepository {
  override fun getAllHabits(): Flow<List<HabitEntity>> {
    return habitDao.getAllHabits()
  }

  override suspend fun getHabitById(id: String): HabitEntity {
    return habitDao.getHabitById(id)
  }

  override suspend fun insertHabit(habit: HabitEntity) {
    habitDao.insertHabit(habit)
  }

  override suspend fun deleteHabit(habit: HabitEntity) {
    habitDao.deleteHabit(habit)
  }
}

