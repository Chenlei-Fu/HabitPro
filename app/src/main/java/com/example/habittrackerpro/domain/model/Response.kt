package com.example.habittrackerpro.domain.model

/**
 * A generic wrapper class for representing responses from data sources like APIs or databases.
 * It can hold either a success with data or a failure with an error message.
 * This makes handling loading, success, and error states in the UI much cleaner.
 */
sealed class Response<out T> {
  /** Represents a loading state, typically used before the result is available. */
  object Loading : Response<Nothing>()
  /** Represents a successful response, containing the data. */
  data class Success<out T>(val data: T) : Response<T>()
  /** Represents a failure, containing an error message. */
  data class Failure(val message: String) : Response<Nothing>()
}
