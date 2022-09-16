object Versions {

    const val kotlinVersion = "1.4.21"
    const val koin = "3.1.5"
    const val timber = "5.0.1"
    const val ktx = "1.9.0-alpha01"
    const val espresso = "3.4.0-alpha02"
    const val annotation = "1.2.0-alpha01"
}

object Libraries {

    const val koin = "io.insert-koin:koin-android:${Versions.koin}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val kotlinStandardLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlinVersion}"
    const val ktx = "androidx.core:core-ktx:${Versions.ktx}"

    // Test libraries
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val annotation = "androidx.annotation:annotation:${Versions.annotation}"
}

object BuildModules {
    const val coreModule = ":core"
    const val networkModule = ":network"
}