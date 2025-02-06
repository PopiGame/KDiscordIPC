plugins {
    kotlin("jvm") version "2.1.20-Beta2"
    kotlin("plugin.serialization") version "2.1.20-Beta2"

    `maven-publish`
}

group = "dev.cbyrne"
version = "0.2.2"

repositories {
    mavenCentral()
}

sourceSets {
    create("example")
}

val exampleImplementation by configurations
exampleImplementation.extendsFrom(configurations.implementation.get())

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.1.20-Beta2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")

    implementation("org.slf4j:slf4j-api:2.0.16")
    implementation("com.kohlschutter.junixsocket:junixsocket-core:2.10.1")

    exampleImplementation(sourceSets.main.get().output)

    // Log4J is only used in the example project as a backend for SLF4j
    exampleImplementation("org.apache.logging.log4j:log4j-api:2.24.3")
    exampleImplementation("org.apache.logging.log4j:log4j-core:2.24.3")
    exampleImplementation("org.apache.logging.log4j:log4j-slf4j-impl:2.24.3")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-opt-in=kotlinx.serialization.ExperimentalSerializationApi"
    kotlinOptions.freeCompilerArgs += "-opt-in=kotlinx.serialization.InternalSerializationApi"
}
