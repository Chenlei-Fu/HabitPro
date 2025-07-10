package com.example.habittrackerpro.presentation.auth.signup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.habittrackerpro.domain.model.Response

@Composable
fun SignupScreen(
  onSignupSuccess: () -> Unit,
  onNavigateToLogin: () -> Unit,
  viewModel: SignupViewModel = hiltViewModel()
) {
  val state by viewModel.state.collectAsState()
  val snackbarHostState = remember { SnackbarHostState() }
  var passwordVisible by remember { mutableStateOf(false) }

  Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->
    Column(
      modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text("Create Account", style = MaterialTheme.typography.headlineLarge)
      Spacer(modifier = Modifier.height(32.dp))

      TextField(
        value = state.email,
        onValueChange = { viewModel.onEvent(SignupEvent.EmailChanged(it)) },
        label = { Text("Email") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
      )

      Spacer(modifier = Modifier.height(8.dp))

      TextField(
        value = state.pass,
        onValueChange = { viewModel.onEvent(SignupEvent.PasswordChanged(it)) },
        label = { Text("Password") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
          val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
          val description = if (passwordVisible) "Hide password" else "Show password"
          IconButton(onClick = {passwordVisible = !passwordVisible}){
            Icon(imageVector  = image, description)
          }
        }
      )

      Spacer(modifier = Modifier.height(16.dp))

      Button(onClick = { viewModel.onEvent(SignupEvent.Signup) }) {
        Text("Sign Up")
      }

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = "Already have an account? Log in.",
        modifier = Modifier.clickable { onNavigateToLogin() },
        color = MaterialTheme.colorScheme.primary,
        textDecoration = TextDecoration.Underline
      )

      when (val response = state.signupResponse) {
        is Response.Loading -> CircularProgressIndicator()
        is Response.Success -> LaunchedEffect(Unit) { onSignupSuccess() }
        is Response.Failure -> LaunchedEffect(response) {
          snackbarHostState.showSnackbar(response.message)
        }
        null -> {}
      }
    }
  }
}