plugins {
    kotlin("js") version "1.7.20"
    kotlin("plugin.serialization") version "1.7.20"
}

group = "uranus.solar.team"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react:18.2.0-pre.413")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:18.2.0-pre.413")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-styled:5.3.6-pre.413")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-router-dom:6.3.0-pre.413")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("io.sunland:kotlin-antd:4.8.6-pre.16-kotlin-1.6.10")
    implementation("io.sunland:kotlin-moment:2.29.1-pre.15-kotlin-1.6.10")
}

kotlin {
    js(LEGACY) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
}