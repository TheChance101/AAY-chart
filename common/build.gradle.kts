plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.android.library)
}

group = "com.aay"
version = "1.0-SNAPSHOT"

kotlin {
    androidTarget()

    jvm("desktop")

    sourceSets {
        commonMain.dependencies {
            implementation(project(":chart"))
            api(compose.runtime)
            api(compose.foundation)
            api(compose.material)
        }

        androidMain.dependencies {
            api(libs.appCompat)
            api(libs.compose.activity)
        }

        sourceSets["desktopMain"].dependencies {
            api(compose.preview)
        }

    }
}

android {
    compileSdk = 34

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {
        minSdk = 21
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlin.jvmToolchain(8)

    namespace = "com.aay.common"
}