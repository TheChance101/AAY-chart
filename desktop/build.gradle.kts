import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
}

group = "com.aay"
version = "1.0-SNAPSHOT"


kotlin {
    jvm {
        withJava()
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
        jvmMain.dependencies {
            implementation(project(":common"))
            implementation(compose.desktop.currentOs)
            implementation("org.jetbrains.skiko:skiko-awt-runtime-$target:$version")
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "AAY-Chart"
            packageVersion = "1.0.0"
        }
    }
}
