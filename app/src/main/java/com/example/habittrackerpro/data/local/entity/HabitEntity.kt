package com.example.habittrackerpro.data.local.entity
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class HabitEntity(
  @PrimaryKey
  val id: String,
  val name: String,
  val frequency: List<Int>,
  val completedDates: List<Long>,
  val reminder: Long,
  val startDate: Long
)
