package com.example.habittrackerpro.di

import android.app.Application
import androidx.room.Room
import com.example.habittrackerpro.data.auth.AuthRepositoryImpl
import com.example.habittrackerpro.domain.auth.AuthRepository
import com.example.habittrackerpro.data.local.HabitDatabase
import com.example.habittrackerpro.data.repository.HabitRepositoryImpl
import com.example.habittrackerpro.domain.repository.HabitRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module that provides application-level dependencies.
 * These dependencies are available throughout the entire application lifecycle.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

  /**
   * Provides a singleton instance of the HabitDatabase.
   * @param app The application context provided by Hilt.
   * @return The singleton HabitDatabase instance.
   */
  @Provides
  @Singleton
  fun provideHabitDatabase(app: Application): HabitDatabase {
    return Room.databaseBuilder(
      app,
      HabitDatabase::class.java,
      "habit_db"
    ).build()
  }

  /**
   * Provides a singleton instance of the HabitRepository.
   * This function tells Hilt that whenever HabitRepository is requested,
   * it should provide an instance of HabitRepositoryImpl.
   *
   * @param db The HabitDatabase instance provided by Hilt via the function above.
   * @return A singleton instance of HabitRepository.
   */
  @Provides
  @Singleton
  fun provideHabitRepository(db: HabitDatabase): HabitRepository {
    return HabitRepositoryImpl(db.habitDao())
  }

  /**
   * Provides a singleton instance of FirebaseAuth.
   */
  @Provides
  @Singleton
  fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

  /**
   * Provides a singleton instance of the AuthRepository.
   * Binds the AuthRepository interface to its implementation.
   */
  @Provides
  @Singleton
  fun provideAuthRepository(auth: FirebaseAuth): AuthRepository {
    return AuthRepositoryImpl(auth)
  }
}

