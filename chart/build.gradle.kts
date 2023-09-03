group = "com.aay"
version = "1.0-SNAPSHOT"
val pkgUrl = "https://github.com/TheChance101/AAY-chart"
val gitUrl = "github.com:TheChance101/AAY-chart.git"

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.android.library")
    id("org.jetbrains.dokka") version "1.5.0"
    id("maven-publish")
    id("signing")
}
repositories {
    google()
    mavenCentral()
}
val dokkaOutputDir = "$buildDir/dokka"
tasks.dokkaHtml {
    outputDirectory.set(file(dokkaOutputDir))
}
val dokkaJar by tasks.creating(Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    archiveClassifier.set("javadoc")
    from(tasks.dokkaHtml)
}

kotlin {
    android()
    jvm("desktop") {
        jvmToolchain(11)
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                api("androidx.appcompat:appcompat:1.5.1")
                api("androidx.core:core-ktx:1.9.0")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation("junit:junit:4.13.2")
            }
        }
        val desktopMain by getting {
            dependencies {
                api(compose.preview)
            }
        }
        val desktopTest by getting
    }
}

android {
    compileSdkVersion(34)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(34)
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
publishing {
    repositories {
        maven {
            name = "Oss"
            setUrl {
                "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
            }
            credentials {
                username = System.getenv("SONATYPE_USERNAME")
                password = System.getenv("SONATYPE_PASSWORD")
            }
        }
        maven {
            name = "Snapshot"
            setUrl { "https://s01.oss.sonatype.org/content/repositories/snapshots/" }
            credentials {
                username = System.getenv("SONATYPE_USERNAME")
                password = System.getenv("SONATYPE_PASSWORD")
            }
        }
    }

    publications {
        publications.configureEach {
            if (this is MavenPublication) {
                artifact(dokkaJar)
                pom {
                    name.set("AAY-Charts")
                    description.set("Revenue Monster Kotlin Multiplatform SDK")
                    url.set("https://github.com/TheChance101/AAY-chart")

                    scm {
                        connection.set("scm:git:git://$gitUrl")
                        developerConnection.set("scm:git:ssh://$gitUrl")
                        url.set(pkgUrl)
                    }
                }
            }
        }
    }
}
if (System.getenv("GPG_PRIVATE_KEY") != null && System.getenv("GPG_PRIVATE_PASSWORD") != null) {
    signing {
        useInMemoryPgpKeys(
            System.getenv("GPG_PRIVATE_KEY"),
            System.getenv("GPG_PRIVATE_PASSWORD")
        )
        sign(publishing.publications)
    }
}
