package com.example.habittrackerpro.data.local

import androidx.room.TypeConverter

class HomeConverters {
  @TypeConverter
  fun fromIntList(list: List<Int>): String {
    return list.joinToString(",")
  }

  @TypeConverter
  fun toIntList(string: String): List<Int> {
    // special handler for empty string case
    if (string.isBlank()) {
      return emptyList()
    }
    return string.split(",").map { it.toInt() }
  }

  @TypeConverter
  fun fromLongList(list: List<Long>): String {
    return list.joinToString(",")
  }

  @TypeConverter
  fun toLongList(string: String): List<Long> {
    if (string.isBlank()) {
      return emptyList()
    }
    return string.split(",").map { it.toLong() }
  }
}