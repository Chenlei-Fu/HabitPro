plugins {
  id("com.android.application") // 不再需要 version
  id("org.jetbrains.kotlin.android") // 不再需要 version
  id("kotlin-kapt")
  id("com.google.devtools.ksp")
  id("com.google.dagger.hilt.android")
  id("com.google.gms.google-services")
}

android {
  namespace = "com.example.habittrackerpro"
  compileSdk = 35

  defaultConfig {
    applicationId = "com.example.habittrackerpro"
    minSdk = 24
    targetSdk = 35
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
  kotlinOptions {
    jvmTarget = "17"
  }
  buildFeatures {
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.5.8" // <-- UPDATED to be compatible with Kotlin 1.9.22
  }
  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

dependencies {
  // Android & Jetpack Compose
  implementation("androidx.core:core-ktx:1.12.0")
  implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
  implementation("androidx.activity:activity-compose:1.8.2")
  implementation(platform("androidx.compose:compose-bom:2023.08.00"))
  implementation("androidx.compose.ui:ui")
  implementation("androidx.compose.ui:ui-graphics")
  implementation("androidx.compose.ui:ui-tooling-preview")
  implementation("androidx.compose.material3:material3")

  // Room for database
  implementation("androidx.room:room-runtime:2.6.1")
  implementation("androidx.room:room-ktx:2.6.1")
  ksp("androidx.room:room-compiler:2.6.1")

  // Hilt for Dependency Injection
  implementation("com.google.dagger:hilt-android:2.48")
  kapt("com.google.dagger:hilt-android-compiler:2.48")
  implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

  // Firebase for Authentication
  implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
  implementation("com.google.firebase:firebase-auth-ktx")

  // Testing
  testImplementation("junit:junit:4.13.2")
  testImplementation("com.google.truth:truth:1.1.3") // 用于本地测试的Truth库
  androidTestImplementation("androidx.test.ext:junit:1.1.5")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
  androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
  androidTestImplementation("androidx.compose.ui:ui-test-junit4")
  androidTestImplementation("com.google.truth:truth:1.1.3") // 用于安卓测试的Truth库
  debugImplementation("androidx.compose.ui:ui-tooling")
  debugImplementation("androidx.compose.ui:ui-test-manifest")
}

// 在dependencies代码块之后，但在文件末尾之前，添加这个
kapt {
  correctErrorTypes = true
}