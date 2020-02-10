

internal object Versions {
    const val kotlin = "1.3.41"
    val androidx = "1.1.0"
    val retrofit = "2.3.0"
    val coreKtx = androidx
    val koin = "2.0.1"
    val junit = "4.12"
    val coroutines = "1.3.2"
    val constrainLayout = "1.1.3"
    val compileSDK = 29
    val minSDK = 21
    val targetSDK = compileSDK
    val buildTools = "29.0.2"
    val mockk = "1.9"
    val androidxArch = "2.1.0"
    val coreTesting = androidxArch
}

object Libs {
    val kotlinJDK = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    val appcompat = "androidx.appcompat:appcompat:${Versions.androidx}"
    val coreKts = "androidx.core:core-ktx:${Versions.coreKtx}"
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val koinViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
    val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constrainLayout}"
    val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    val androidCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    val junit = "junit:junit:${Versions.junit}"
    val coreTesting = "androidx.arch.core:core-testing:${Versions.coreTesting}"
    val corountinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    val mockk = "io.mockk:mockk:${Versions.mockk}"
}