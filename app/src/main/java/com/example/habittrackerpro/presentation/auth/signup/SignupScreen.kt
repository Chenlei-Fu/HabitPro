package com.example.habittrackerpro.presentation.auth.signup

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
fun SignupScreen(
  onSignupSuccess: () -> Unit,
  onNavigateToLogin: () -> Unit,
  viewModel: SignupViewModel = hiltViewModel()
) {
  val state by viewModel.state.collectAsState()
  val snackbarHostState = remember { SnackbarHostState() }

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
        label = { Text("Email") }
      )
      Spacer(modifier = Modifier.height(8.dp))
      TextField(
        value = state.pass,
        onValueChange = { viewModel.onEvent(SignupEvent.PasswordChanged(it)) },
        label = { Text("Password") }
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
