package com.example.habittrackerpro.presentation.auth.login

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
fun LoginScreen(
  onLoginSuccess: () -> Unit,
  onNavigateToSignup: () -> Unit,
  viewModel: LoginViewModel = hiltViewModel()
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
      Text("Login", style = MaterialTheme.typography.headlineLarge)
      Spacer(modifier = Modifier.height(32.dp))

      TextField(
        value = state.email,
        onValueChange = { viewModel.onEvent(LoginEvent.EmailChanged(it)) },
        label = { Text("Email") },
        // Set the keyboard type to Email for better user experience
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
      )

      Spacer(modifier = Modifier.height(8.dp))

      TextField(
        value = state.pass,
        onValueChange = { viewModel.onEvent(LoginEvent.PasswordChanged(it)) },
        label = { Text("Password") },
        // Set keyboard type to Password
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        // Hide or show password based on the passwordVisible state
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        // Add a trailing icon to toggle password visibility
        trailingIcon = {
          val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
          val description = if (passwordVisible) "Hide password" else "Show password"
          IconButton(onClick = { passwordVisible = !passwordVisible }) {
            Icon(imageVector = image, contentDescription = description)
          }
        }
      )

      Spacer(modifier = Modifier.height(16.dp))

      Button(onClick = { viewModel.onEvent(LoginEvent.Login) }) {
        Text("Login")
      }

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = "Don't have an account? Sign up.",
        modifier = Modifier.clickable { onNavigateToSignup() },
        color = MaterialTheme.colorScheme.primary,
        textDecoration = TextDecoration.Underline
      )

      when (val response = state.loginResponse) {
        is Response.Loading -> CircularProgressIndicator()
        is Response.Success -> LaunchedEffect(Unit) { onLoginSuccess() }
        is Response.Failure -> LaunchedEffect(response) {
          snackbarHostState.showSnackbar(response.message)
        }
        null -> {}
      }
    }
  }
}