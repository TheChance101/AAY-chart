import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.compose)
    alias(libs.plugins.composePlugin)
}

kotlin {
    wasmJs {
        outputModuleName = "wasm"
        browser {
            commonWebpackConfig {
                outputFileName = "wasm.js"
                mode = KotlinWebpackConfig.Mode.PRODUCTION
                sourceMaps = false
            }
        }
        binaries.executable()
    }

    sourceSets {
        val commonMain by getting{
            dependencies {
                implementation(project(":chart"))
                implementation(project(":common"))
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
            }
        }
    }
}