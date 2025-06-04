package com.example.habittrackerpro.domain.repository

import com.example.habittrackerpro.data.local.entity.HabitEntity
import kotlinx.coroutines.flow.Flow

/**
 * Defines the contract for the habit data repository.
 * This interface acts as a clean API for the domain and presentation layers
 * to interact with habit data, abstracting away the data source.
 */

interface HabitRepository {
  /** Retrieves all habits as a reactive stream. */
  fun getAllHabits(): Flow<List<HabitEntity>>

  /** Retrieves a single habit by its ID. */
  suspend fun getHabitById(id: String): HabitEntity

  /** Inserts or updates a habit. */
  suspend fun insertHabit(habit: HabitEntity)

  /** Deletes a habit. */
  suspend fun deleteHabit(habit: HabitEntity)

}