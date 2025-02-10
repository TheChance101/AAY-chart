pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

rootProject.name = "AAY-chart"

include(":android", ":desktop", ":common", ":chart")
includeBuild("convention-plugins")
