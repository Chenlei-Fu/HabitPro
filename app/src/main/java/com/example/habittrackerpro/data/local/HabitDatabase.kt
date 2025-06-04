package com.example.habittrackerpro.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.habittrackerpro.data.local.entity.HabitEntity

@Database(
  entities = [HabitEntity::class],
  version = 1,
  exportSchema = false
)
@TypeConverters(HomeConverters::class)
abstract class HabitDatabase : RoomDatabase() {
  abstract fun habitDao(): HabitDao
}
