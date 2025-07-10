package com.example.habittrackerpro.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
  onBack: () -> Unit,
  onLogout: () -> Unit,
  viewModel: ProfileViewModel = hiltViewModel()
) {
  val state by viewModel.state.collectAsStateWithLifecycle()

  // Use DisposableEffect to safely add and remove the auth state listener.
  // This is the correct way to react to auth changes from outside the composable.
  DisposableEffect(Unit) {
    val auth = FirebaseAuth.getInstance()
    val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
      if (firebaseAuth.currentUser == null) {
        // User has logged out, trigger the navigation callback.
        onLogout()
      }
    }
    auth.addAuthStateListener(listener)

    // onDispose is called when the composable leaves the screen.
    // It's crucial to remove the listener to avoid memory leaks.
    onDispose {
      auth.removeAuthStateListener(listener)
    }
  }

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Profile") },
        navigationIcon = {
          IconButton(onClick = onBack) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
          }
        }
      )
    }
  ) { paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .padding(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Text(text = "Logged in as:", style = MaterialTheme.typography.bodyLarge)
      Spacer(modifier = Modifier.height(8.dp))
      Text(
        text = state.email,
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold
      )
      Spacer(modifier = Modifier.height(32.dp))
      Button(
        onClick = { viewModel.onEvent(ProfileEvent.OnLogout) },
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
      ) {
        Text(text = "Logout")
      }
    }
  }
}
