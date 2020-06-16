plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    jcenter()
    google()
    maven {
        url = uri("https://plugins.gradle.org/m2/")
    }
}

dependencies {
    /* Depend on the kotlin plugin, since we want to access it in our plugin */
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.71")

    /* Depend on the android gradle plugin, since we want to access it in our plugin */
    implementation("com.android.tools.build:gradle:4.0.0")

    implementation("org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:2.8.0.1969")

    implementation("org.jacoco:org.jacoco.core:0.8.5")

    implementation("com.hiya:jacoco-android:0.2")

    /* Depend on the default Gradle API's since we want to build a custom plugin */
    implementation(gradleApi())
    implementation(localGroovy())
}

// Added to overcome https://github.com/mockk/mockk/issues/281 for now.
// Seems to be fixed in mockk version 1.10.0
// TODO: Remove this after testing
allprojects {
    configurations.all {
        resolutionStrategy {
            force("org.objenesis:objenesis:2.6")
        }
    }
}