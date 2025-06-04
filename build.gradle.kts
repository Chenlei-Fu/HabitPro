// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
  id("com.android.application") version "8.2.0" apply false
  id("org.jetbrains.kotlin.android") version "1.9.22" apply false // <-- UPDATED
  id("com.google.devtools.ksp") version "1.9.22-1.0.17" apply false // <-- UPDATED to match Kotlin version
  id("com.google.dagger.hilt.android") version "2.48" apply false // Hilt version is stable
  id("com.google.gms.google-services") version "4.4.1" apply false // Google Services version is stable
}