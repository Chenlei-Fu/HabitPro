package com.example.habittrackerpro.presentation.auth.login

import com.example.habittrackerpro.domain.model.Response

data class LoginState(
  val email: String = "",
  val pass: String = "",
  val loginResponse: Response<Boolean>? = null
)