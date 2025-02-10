plugins {
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.android.application)
    alias(libs.plugins.android.kotlin)
}

group = "com.aay"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(project(":common"))
    implementation(libs.compose.activity)
}

android {
    compileSdk = 34

    defaultConfig {
        applicationId = "com.aay.android"
        minSdk = 24
        versionCode = 1
        versionName = "1.0-SNAPSHOT"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    kotlin.jvmToolchain(8)

    namespace = "com.aay.android"

}