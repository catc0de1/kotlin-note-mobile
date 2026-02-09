plugins {
  id("com.android.application")
  kotlin("android")
  kotlin("kapt")
}

android {
  namespace = "com.catcode.note_app"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.catcode.noteapp"
    minSdk = 26
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  buildTypes {
    getByName("debug") {
      isMinifyEnabled = false
    }
  }

  // buildFeatures {
  //   compose = true
  // }

  // composeOptions {
  //   kotlinCompilerExtensionVersion = "1.5.8"
  // }

  kotlinOptions {
    jvmTarget = "17"
  }

  buildFeatures {
    viewBinding = true
  }
}

dependencies {
  implementation("androidx.core:core-ktx:1.12.0")
  // implementation("androidx.activity:activity-compose:1.8.2")

  // implementation(platform("androidx.compose:compose-bom:2024.01.00"))
  // implementation("androidx.compose.ui:ui")
  // implementation("androidx.compose.material3:material3")
  // implementation("androidx.compose.ui:ui-tooling-preview")

  // debugImplementation("androidx.compose.ui:ui-tooling")

  implementation("androidx.appcompat:appcompat:1.6.1")

  // Material UI klasik
  implementation("com.google.android.material:material:1.11.0")

  implementation("androidx.room:room-runtime:2.6.1")
  kapt("androidx.room:room-compiler:2.6.1")

  implementation("androidx.room:room-ktx:2.6.1")

  implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
  implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

  // Layout
  implementation("androidx.constraintlayout:constraintlayout:2.1.4")

  // List
  implementation("androidx.recyclerview:recyclerview:1.3.2")
}
