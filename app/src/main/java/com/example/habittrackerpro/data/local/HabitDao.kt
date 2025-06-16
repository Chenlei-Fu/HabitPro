package com.example.habittrackerpro.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.habittrackerpro.data.local.entity.HabitEntity
import kotlinx.coroutines.flow.Flow
@Dao
interface HabitDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertHabit(habit: HabitEntity)

  @Query("SELECT * FROM HabitEntity")
  fun getAllHabits(): Flow<List<HabitEntity>>

  @Query("SELECT * FROM HabitEntity WHERE id = :id")
  suspend fun getHabitById(id: String): HabitEntity

  @Delete
  suspend fun deleteHabit(habit: HabitEntity)

  @Update
  suspend fun updateHabit(habit: HabitEntity)
}