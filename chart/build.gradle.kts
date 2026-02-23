import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.compose)
    alias(libs.plugins.composePlugin)
    id("com.android.library")
    alias(libs.plugins.dokka)
    id("convention.publication")
    kotlin("native.cocoapods")
}

group = "io.github.thechance101"
version = "1.2.0"

kotlin {
    jvmToolchain {
        this.languageVersion.set(JavaLanguageVersion.of(11))
    }

    androidTarget {
        publishLibraryVariants("release")
    }
    jvm("desktop")

    iosX64 {
        binaries {
            framework("SharedFrameworkIosX64") {
                baseName = "SharedFramework"
            }
        }
    }

    iosArm64 {
        binaries {
            framework("SharedFrameworkIosArm64") {
                baseName = "SharedFramework"
            }
        }
    }

    iosSimulatorArm64 {
        binaries {
            framework("SharedFrameworkIosSimulatorArm64") {
                baseName = "SharedFramework"
            }
        }
    }

    js(IR) {
        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                    webpackConfig.cssSupport {
                        enabled.set(true)
                    }
                }
            }
        }
        binaries.executable()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "chart"
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
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
            }
        }

        val androidMain by getting {
            dependencies {
                api(libs.appcompat)
                api(libs.core.ktx)
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
                implementation("org.jetbrains.kotlinx:kotlinx-browser:0.1")
            }
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting

        val jsMain by getting
    }
}

android {
    compileSdkVersion(34)
    namespace = "com.aay"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {
        manifestPlaceholders["TheChance101"] = "io.github.thechance101"
        minSdkVersion(21)
        targetSdkVersion(34)
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

// Fix for Gradle implicit dependency error (Race condition between Signing and Publishing)
tasks.withType<PublishToMavenLocal>().configureEach {
    val signingTasks = tasks.withType<Sign>()
    mustRunAfter(signingTasks)
}

tasks.withType<PublishToMavenRepository>().configureEach {
    val signingTasks = tasks.withType<Sign>()
    mustRunAfter(signingTasks)
}