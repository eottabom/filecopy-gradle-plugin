import java.util.Properties

plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    `maven-publish`
    kotlin("jvm") version "1.9.22"
    id("com.gradle.plugin-publish") version "1.2.1"
}

val versionProps = Properties().apply {
    load(file("version.properties").inputStream())
}

group = "io.github.eottabom"
version = versionProps["version"] as String

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

gradlePlugin {
    website = "https://github.com/eottabom/filecopy-gradle-plugin"
    vcsUrl = "https://github.com/eottabom/filecopy-gradle-plugin"

    plugins {
        create("fileCopyPlugin") {
            id = "io.github.eottabom.filecopy-gradle-plugin"
            displayName = "FileCopy Gradle Plugin"
            description = "A Gradle plugin that copies files based on regex pattern matching rules."
            tags = listOf("filecopy", "copy", "file", "regex")
            implementationClass = "io.github.eottabom.FileCopyPlugin"
        }
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/eottabom/filecopy-gradle-plugin")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
}