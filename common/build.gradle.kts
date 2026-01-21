import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.compose)
    alias(libs.plugins.composePlugin)
    id("com.android.library")
}

group = "com.aay"
version = "1.0-SNAPSHOT"

kotlin {
    jvm("desktop")

    androidTarget {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
    }

    // WASM Target
    wasmJs {
        outputModuleName = "composeApp"
        browser {
            commonWebpackConfig {
                outputFileName = "AAY-Chart.js"
                mode = KotlinWebpackConfig.Mode.PRODUCTION
                sourceMaps = false
            }
        }
        binaries.executable()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("io.github.thechance101:chart:1.0")
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
            }
        }

        val androidMain by getting {
            dependencies {
                api("androidx.appcompat:appcompat:1.7.0")
                api("androidx.core:core-ktx:1.9.0")
            }
        }

        val desktopMain by getting {
            dependencies {
                api(compose.preview)
            }
        }

        val wasmJsMain by getting {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
            }
        }
    }
}

android {
    namespace = "com.aay"
    compileSdkVersion(34)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(34)
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
