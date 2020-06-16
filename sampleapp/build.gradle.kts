plugins {
    id("com.android.application")
    id("dev.nikhi1.plugin.android")
}

android {

    defaultConfig {
        applicationId = "dev.nikhi1.sampleapp"
        versionCode = 1
        versionName = "1.0"
        resConfigs("en")
    }
}

dependencies {
    implementation(project(":samplelib"))
}