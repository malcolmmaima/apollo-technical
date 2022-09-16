pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    plugins {
        id("com.android.application") version "7.1.0"
        id("com.android.library") version "7.1.0"
        id("org.jetbrains.kotlin.android") version "1.6.10"
    }

    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "com.android.application", "com.android.library" -> useModule("com.android.tools.build:gradle:7.1.3")
            }
        }
    }
}

include("app")

include(":core")
include(":network")
rootProject.name = "takehomeassignment"
