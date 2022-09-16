plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.hiya.jacoco-android")
    id("de.mannodermaus.android-junit5")
}

jacoco {
    toolVersion = "0.8.4"
}

android {

    compileSdk = 31

    defaultConfig {
        minSdk = 21
        targetSdk = 31
        vectorDrawables.useSupportLibrary = true
    }

    testOptions {
        animationsDisabled = true
        unitTests.apply {
            isReturnDefaultValues = true
            isIncludeAndroidResources = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
        }

        getByName("release") {
            isMinifyEnabled = true
            proguardFiles("proguard-android.txt", "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Libraries.kotlinStandardLib)
    implementation(Libraries.ktx)
    implementation(Libraries.koin)
    implementation(Libraries.timber)

    api("com.squareup.retrofit2:retrofit:2.9.0")
    api("com.squareup.okhttp3:logging-interceptor:4.9.3")
    api("com.squareup.retrofit2:converter-gson:2.9.0")
    api("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")

    debugImplementation("com.github.chuckerteam.chucker:library:3.5.2")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:3.5.2")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")


}