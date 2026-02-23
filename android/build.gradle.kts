plugins {
    alias(libs.plugins.compose)
    id("com.android.application")
    kotlin("android")
    alias(libs.plugins.composePlugin)
}

group = "com.aay"
version = "1.0"

repositories {
    jcenter()
}

dependencies {
    implementation(project(":common"))
    implementation("androidx.activity:activity-compose:1.7.0")
}

android {
    namespace = "com.aay.android"
    compileSdkVersion(34)
    defaultConfig {
        applicationId = "com.aay.android"
        minSdkVersion(24)
        targetSdkVersion(34)
        versionCode = 1
        versionName = "1.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

