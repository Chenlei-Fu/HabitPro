package com.example.habittrackerpro.domain.model

import java.util.UUID

/**
 * Represents a Habit in the domain layer and for the UI.
 * This is a clean data class, free of any database or framework-specific annotations.
 *
 * @param id The unique identifier.
 * @param name The name of the habit.
 */
data class Habit(
  val id: String = UUID.randomUUID().toString(),
  val name: String
)
