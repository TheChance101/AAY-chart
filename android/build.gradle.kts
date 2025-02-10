plugins {
    id("org.jetbrains.compose")
    id("com.android.application")
    kotlin("android")
    id("org.jetbrains.kotlin.plugin.compose")
}

group = "com.aay"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(project(":common"))
    implementation("androidx.activity:activity-compose:1.9.3")
}

android {
    compileSdkVersion(34)
    defaultConfig {
        applicationId = "com.aay.android"
        minSdkVersion(24)
        targetSdkVersion(34)
        versionCode = 1
        versionName = "1.0-SNAPSHOT"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    kotlin {
        jvmToolchain(8)
    }
    namespace = "com.aay.android"

}