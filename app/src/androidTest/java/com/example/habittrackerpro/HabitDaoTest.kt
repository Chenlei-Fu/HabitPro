package com.example.habittrackerpro

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.habittrackerpro.data.local.HabitDao
import com.example.habittrackerpro.data.local.HabitDatabase
import com.example.habittrackerpro.data.local.entity.HabitEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Instrumented test for the HabitDao.
 * This class tests all database operations defined in the DAO.
 */
@RunWith(AndroidJUnit4::class) // Specifies the test runner for Android instrumented tests.
class HabitDaoTest {

  private lateinit var habitDao: HabitDao
  private lateinit var db: HabitDatabase

  /**
   * This function is executed before each test case.
   * It creates an in-memory version of the database, which is ideal for testing
   * as it's fast and gets destroyed after the test run.
   */
  @Before
  fun createDb() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    db = Room.inMemoryDatabaseBuilder(
      context,
      HabitDatabase::class.java
    ).build()
    habitDao = db.habitDao()
  }

  /**
   * This function is executed after each test case.
   * It closes the database to release resources.
   */
  @After
  @Throws(IOException::class)
  fun closeDb() {
    db.close()
  }

  /**
   * Test case to verify that inserting a habit and then retrieving all habits works correctly.
   * It follows the Arrange-Act-Assert pattern.
   */
  @Test
  @Throws(Exception::class)
  fun insertAndReadHabit() = runBlocking { // runBlocking is used to run suspend functions in a blocking way for tests.
    // Arrange: Create a sample habit.
    val habit = HabitEntity(
      id = "1",
      name = "Read a book",
      frequency = listOf(1, 2, 3), // Mon, Tue, Wed
      completedDates = emptyList(),
      reminder = 0L,
      startDate = 0L
    )

    // Act: Insert the habit into the database.
    habitDao.insertHabit(habit)

    // Assert: Retrieve all habits and verify the inserted habit is present.
    // .first() is a terminal operator for Flow that collects the first emitted value.
    val allHabits = habitDao.getAllHabits().first()

    // Using Google's Truth library for more readable assertions.
    assertThat(allHabits).contains(habit)
    assertThat(allHabits).hasSize(1)
  }
}
