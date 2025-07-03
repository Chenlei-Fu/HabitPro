package com.example.habittrackerpro.presentation.auth.signup

import com.example.habittrackerpro.domain.model.Response

data class SignupState(
  val email: String = "",
  val pass: String = "",
  val signupResponse: Response<Boolean>? = null
)
