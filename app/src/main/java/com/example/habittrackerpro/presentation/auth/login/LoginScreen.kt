package com.example.habittrackerpro.presentation.auth.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        label = { Text("Email") }
      )
      Spacer(modifier = Modifier.height(8.dp))
      TextField(
        value = state.pass,
        onValueChange = { viewModel.onEvent(LoginEvent.PasswordChanged(it)) },
        label = { Text("Password") }
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

      // Handle the login response
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
