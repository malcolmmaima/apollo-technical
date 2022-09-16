plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("org.jlleitschuh.gradle.ktlint")
}

android {
    compileSdk = 31
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "com.apolloagriculture.android.takehomeassignment"
        minSdk = 21
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    val APP_ID: String? =
        com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir).getProperty("APP_ID")

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            versionNameSuffix = " - debug"
            applicationIdSuffix = ".debug"
            signingConfig = signingConfigs.getByName("debug")
            buildConfigField("String", "APP_ID", APP_ID.toString())
        }
        getByName("release") {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "APP_ID", APP_ID.toString())
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }

    dependencies {
        implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

        implementation(Libraries.ktx)
        implementation(Libraries.kotlinStandardLib)
        api(project(BuildModules.networkModule))
        implementation("androidx.appcompat:appcompat:1.4.1")
        implementation("com.google.android.material:material:1.5.0")
        implementation("androidx.constraintlayout:constraintlayout:2.1.0")
        implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
        implementation("androidx.navigation:navigation-ui-ktx:2.3.5")
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")
        implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")

        implementation(Libraries.koin)
        implementation(Libraries.timber)

        testImplementation("io.mockk:mockk:1.12.2")
        testImplementation("io.mockk:mockk-agent-jvm:1.12.2")

        testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
        testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.6.0")

        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.1.3")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    }
}