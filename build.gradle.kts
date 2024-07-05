import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    val kotlinVersion: String by System.getProperties()
    kotlin("plugin.serialization") version kotlinVersion
    kotlin("multiplatform") version kotlinVersion
    val kvisionVersion: String by System.getProperties()
    id("io.kvision") version kvisionVersion
}

version = "1.0.0-SNAPSHOT"
group = "com.github.stephenhamiltonc"

repositories {
    mavenCentral()
    mavenLocal()
}

// Versions
val kotlinJsonVersion: String by System.getProperties()
val kotlinVersion: String by System.getProperties()
val kotlinDatetimeVersion: String by System.getProperties()
val kvisionVersion: String by System.getProperties()
val navigoKotlinVersion: String by System.getProperties()
val timecardVersion: String by System.getProperties()

val webDir = file("src/main/web")

kotlin {
    js {
        browser {
            commonWebpackConfig {
                outputFileName = "main.bundle.js"
            }
            testTask {
                useKarma {
                    useChromeHeadless()
                    useFirefoxHeadless()
                }
            }
        }
        binaries.executable()
    }
    sourceSets["jsMain"].dependencies {
        // Timecard
        implementation("com.github.stephenhamiltonc:timecard-lib:$timecardVersion")

        // JSON Serialization
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinJsonVersion")
        implementation("org.jetbrains.kotlinx:kotlinx-datetime:$kotlinDatetimeVersion")

        // KVision
        implementation("io.kvision:kvision:$kvisionVersion")
        implementation("io.kvision:kvision-bootstrap:$kvisionVersion")
        implementation("io.kvision:kvision-toastify:$kvisionVersion")
        implementation("io.kvision:kvision-datetime:$kvisionVersion")
        implementation("io.kvision:kvision-bootstrap-icons:$kvisionVersion")
        implementation("io.kvision:kvision-fontawesome:$kvisionVersion")
        implementation("io.kvision:kvision-tabulator:$kvisionVersion")
        implementation("io.kvision:kvision-routing-navigo:$kvisionVersion")
    }
    sourceSets["jsTest"].dependencies {
        implementation(kotlin("test-js"))
        implementation("io.kvision:kvision-testutils:$kvisionVersion")
    }
    sourceSets["jsMain"].resources.srcDir(webDir)
}
