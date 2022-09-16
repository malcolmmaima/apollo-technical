// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    id("com.android.library") apply false
    id("com.android.application") apply false
    id("org.jetbrains.kotlin.android") apply false
    id("org.jlleitschuh.gradle.ktlint") version "10.1.0"
    id("io.gitlab.arturbosch.detekt") version "1.19.0"
    id("com.diffplug.spotless") version "6.2.2"
}

allprojects {

    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }

    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    ktlint {
        android.set(true)
        verbose.set(true)
        filter {
            exclude { element -> element.file.path.contains("generated/") }
        }
    }
}

buildscript {

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21")
        classpath("com.hiya:jacoco-android:0.2")
        classpath("de.mannodermaus.gradle.plugins:android-junit5:1.7.1.1")
        classpath("com.osacky.flank.gradle:fladle:0.16.2")
    }
}

subprojects {

    apply(plugin = "io.gitlab.arturbosch.detekt")
    detekt {
        config = files("${project.rootDir}/detekt.yml")
        parallel = true
        buildUponDefaultConfig = true
    }

    apply(plugin = "com.diffplug.spotless")
    spotless {
        kotlin {
            target("**/*.kt")
            licenseHeaderFile(
                    rootProject.file("${project.rootDir}/spotless/copyright.kt"),
                    "^(package|object|import|interface)"
            )
        }
    }
}