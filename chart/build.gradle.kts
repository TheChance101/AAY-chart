plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.android.library")
    id("org.jetbrains.dokka") version "1.5.0"
    id("convention.publication")
    kotlin("native.cocoapods")
}
group = "io.github.thechance101"
version = "Beta-0.0.3"

kotlin {
    android {
        publishLibraryVariants("release", "debug")
    }
    jvm("desktop") {
        jvmToolchain(11)
    }
    ios{}
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

    val osName = System.getProperty("os.name")
    val targetOs = when {
        osName == "Mac OS X" -> "macos"
        osName.startsWith("Win") -> "windows"
        osName.startsWith("Linux") -> "linux"
        else -> error("Unsupported OS: $osName")
    }

    val targetArch = when (val osArch = System.getProperty("os.arch")) {
        "x86_64", "amd64" -> "x64"
        "aarch64" -> "arm64"
        else -> error("Unsupported arch: $osArch")
    }

    val version = "0.7.77" // or any more recent version
    val target = "${targetOs}-${targetArch}"


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
                api("androidx.appcompat:appcompat:1.5.1")
                api("androidx.core:core-ktx:1.9.0")
            }
        }

        val desktopMain by getting {
            dependencies {
                implementation("org.jetbrains.skiko:skiko-awt-runtime-$target:$version")
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
    compileSdkVersion(34)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        manifestPlaceholders["TheChance101"] = "io.github.thechance101"
        minSdkVersion(21)
        targetSdkVersion(34)
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
        create("staging") {
            initWith(getByName("release"))
            manifestPlaceholders["TheChance101"] = "io.github.thechance101"
        }
    }
}