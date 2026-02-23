plugins {
    id("com.vanniktech.maven.publish")
}

mavenPublishing {
    // Load signing and publishing credentials
    val localProperties = java.util.Properties()
    val localPropertiesFile = project.rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        localPropertiesFile.inputStream().use { localProperties.load(it) }
    }

    // Set properties from local.properties if they are not already set (e.g., from gradle.properties or ENV)
    fun setIfMissing(envKey: String, propertyKey: String) {
        if (!project.hasProperty(propertyKey)) {
            val value = localProperties.getProperty(propertyKey) ?: System.getenv(envKey)
            if (value != null) {
                project.extensions.extraProperties.set(propertyKey, value)
            }
        }
    }

    setIfMissing("MAVEN_CENTRAL_USERNAME", "mavenCentralUsername")
    setIfMissing("MAVEN_CENTRAL_PASSWORD", "mavenCentralPassword")
    setIfMissing("SIGNING_KEY_ID", "signing.keyId")
    setIfMissing("SIGNING_PASSWORD", "signing.password")
    setIfMissing("SIGNING_SECRET_KEY", "signing.secretKey")

    // 1. Define your library's coordinates (Update these to match your project)
    coordinates(
        groupId = "io.github.thechance101",
        artifactId = "aay-chart",
        version = "1.2.0"
    )

    // 2. Provide the POM metadata
    pom {
        name.set("AAY-Chart")
        description.set("Multiplatform library for desktop and android")
        url.set("https://github.com/TheChance101/AAY-chart")
        inceptionYear.set("2023") // Optional, but good practice

        licenses {
            license {
                name.set("MIT")
                url.set("https://opensource.org/licenses/MIT")
            }
        }
        developers {
            developer {
                id.set("TheChance101") // Fixed: Removed the angle brackets
                name.set("The_chance")
                email.set("hana_hany6@yahoo.com")
            }
        }
        scm {
            url.set("https://github.com/TheChance101/AAY-chart")
        }
    }

    // 3. Target the new Maven Central Portal natively
    publishToMavenCentral(com.vanniktech.maven.publish.SonatypeHost.CENTRAL_PORTAL)

    // 4. Automatically sign the artifacts
    signAllPublications()
}
