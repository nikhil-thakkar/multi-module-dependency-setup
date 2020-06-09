

internal object Versions {
    const val kotlin = "1.3.71"
    const val androidx = "1.1.0"
    const val coreKtx = androidx
    const val compileSDK = 29
    const val minSDK = 21
    const val targetSDK = compileSDK
    const val buildTools = "29.0.2"
    const val androidxArch = "2.1.0"
    const val navigation = "2.3.0-alpha04"
    const val constrainLayout = "1.1.3"
    const val material = "1.1.0-rc02"

    const val junit = "4.12"
    const val mockk = "1.9"
    const val coreTesting = androidxArch
    const val testRunner = "1.2.0"
    const val testKtx = "1.3.0-beta02"
    const val espressoCore = "3.1.0"
}

object Libs {
    val kotlinJDK = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    val appcompat = "androidx.appcompat:appcompat:${Versions.androidx}"
    val coreKts = "androidx.core:core-ktx:${Versions.coreKtx}"
    val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constrainLayout}"
    val navigator = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    val material = "com.google.android.material:material:${Versions.material}"

    val junit = "junit:junit:${Versions.junit}"
    val coreTesting = "androidx.arch.core:core-testing:${Versions.coreTesting}"
    val mockk = "io.mockk:mockk:${Versions.mockk}"
    val testRunner = "androidx.test:runner:${Versions.testRunner}"
    val testRules = "androidx.test:rules:${Versions.testRunner}"
    val testKtx = "androidx.test:core-ktx:${Versions.testKtx}"
    val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
}