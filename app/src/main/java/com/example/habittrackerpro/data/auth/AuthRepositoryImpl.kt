package com.example.habittrackerpro.data.auth

import com.example.habittrackerpro.domain.auth.AuthRepository
import com.example.habittrackerpro.domain.model.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * Concrete implementation of AuthRepository using Firebase Authentication.
 * @param auth The FirebaseAuth instance provided by Hilt.
 */
class AuthRepositoryImpl @Inject constructor(
  private val auth: FirebaseAuth
) : AuthRepository {
  override val currentUser: FirebaseUser?
    get() = auth.currentUser

  override suspend fun signIn(email: String, password: String): Response<Boolean> {
    return try {
      auth.signInWithEmailAndPassword(email, password).await()
      Response.Success(true)
    } catch (e: Exception) {
      Response.Failure(e.message ?: "An unknown error occurred.")
    }
  }

  override suspend fun signUp(email: String, password: String): Response<Boolean> {
    return try {
      auth.createUserWithEmailAndPassword(email, password).await()
      Response.Success(true)
    } catch (e: Exception) {
      Response.Failure(e.message ?: "An unknown error occurred.")
    }
  }

  override fun signOut() {
    auth.signOut()
  }
}
