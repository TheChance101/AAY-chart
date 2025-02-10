plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.android.library)
    alias(libs.plugins.dokka)
    alias(libs.plugins.nativeCocoapod)
    id("convention.publication")
}
group = "io.github.thechance101"
version = "Beta-0.0.5"

kotlin {
    androidTarget {
        publishLibraryVariants("release", "debug")
    }

    jvm("desktop")

    ios {}
    iosX64()
    iosArm64()
    iosSimulatorArm64()

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
                api(libs.appCompat)
                api(libs.androidx.core)
            }
        }

        val desktopMain by getting {
            dependencies {
                api(compose.preview)
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by getting {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)

        }
        val jsMain by getting
    }
}

android {
    compileSdk = 34

    defaultConfig {
        manifestPlaceholders["TheChance101"] = "io.github.thechance101"
        minSdk = 21
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlin.jvmToolchain(8)

    namespace = "com.aay.chart"
}