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

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "AAY-chart"
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

    sourceSets {
        commonMain.dependencies {
            api(compose.runtime)
            api(compose.foundation)
            api(compose.material)
        }

        androidMain.dependencies {
            api(libs.appCompat)
            api(libs.androidx.core)
        }

        sourceSets["desktopMain"].dependencies {
            api(compose.preview)
        }

        iosMain.dependencies {}

        jsMain.dependencies {}
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