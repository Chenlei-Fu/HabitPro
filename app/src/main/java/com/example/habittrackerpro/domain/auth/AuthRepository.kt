package com.example.habittrackerpro.domain.auth

import com.example.habittrackerpro.domain.model.Response
import com.google.firebase.auth.FirebaseUser

/**
 * Defines the contract for the authentication repository.
 * This abstracts away the implementation details of the auth provider (e.g., Firebase).
 */
interface AuthRepository {
  /** Read-only property to get the current logged-in user. */
  val currentUser: FirebaseUser?

  /** Signs in a user with the given email and password. */
  suspend fun signIn(email: String, password: String): Response<Boolean>

  /** Signs up a new user with the given email and password. */
  suspend fun signUp(email: String, password: String): Response<Boolean>

  /** Signs out the current user. */
  fun signOut()
}