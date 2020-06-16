import com.android.build.gradle.BaseExtension
import com.hiya.plugins.JacocoAndroidUnitTestReportExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.plugins.JacocoTaskExtension
import org.gradle.testing.jacoco.tasks.JacocoReport
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.sonarqube.gradle.SonarQubeExtension

/***
 * This plugin is instantiated every time when you apply this plugin to build.gradle in library/feature module
 * <code>apply plugin: 'dev.nikhi1.plugin.android'</code>
 * [Note] Don't apply this plugin to pure Java/Kotlin modules. Its a no-op.
 */
class AndroidModulePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        if (project.hasProperty("android")) {
            with(project) {
                plugins.apply("kotlin-android")
                plugins.apply("kotlin-android-extensions")
                plugins.apply("kotlin-kapt")

                configureSonarqube()
                configureJacoco()
                configureAndroidBlock()
                configureCommonDependencies()
                configureTestDependencies()
            }
        }
    }
}

internal fun Project.configureAndroidBlock() {
    extensions.getByType<BaseExtension>().run {

        buildToolsVersion(Versions.buildTools)
        compileSdkVersion(Versions.compileSDK)

        defaultConfig {
            minSdkVersion(Versions.minSDK)
            targetSdkVersion(Versions.targetSDK)
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }


        tasks.withType(KotlinCompile::class.java).all {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_1_8.toString()
            }
        }

        testOptions {
            unitTests.apply {
                isReturnDefaultValues = true
            }
            animationsDisabled = true
        }

        buildTypes {
            getByName("debug") {
                isTestCoverageEnabled = true
            }
        }
    }
}

internal fun Project.configureCommonDependencies() {

    extensions.getByType<BaseExtension>().run {
        dependencies {
            add("implementation", Libs.coreKts)
            add("implementation", Libs.appcompat)
            add("implementation", Libs.constraintLayout)
            add("implementation", Libs.navigator)
            add("implementation", Libs.material)
            add("implementation", Libs.fragment)
            add("implementation", Libs.fragmentKtx)
        }
    }
}

internal fun Project.configureTestDependencies() {
    extensions.getByType<BaseExtension>().run {
        dependencies {
            add("testImplementation", Libs.junit)
            add("testImplementation", Libs.mockk)
            add("testImplementation", Libs.coreTesting)
            add("testImplementation", Libs.kotlinJDK)
            add("debugImplementation", Libs.fragmentTesting)

            add("androidTestImplementation", Libs.testRunner)
            add("androidTestImplementation", Libs.testRules)
            add("androidTestImplementation", Libs.testKtx)
            add("androidTestImplementation", Libs.espressoCore)
            add("androidTestImplementation", Libs.navTesting)
            add("androidTestImplementation", Libs.truth)
        }
    }
}

/**
 * [SonarQube] is the leading tool for continuously inspecting the Code Quality and Security of your codebases and
 * guiding development teams during Code Reviews.
 * These analyses are driven by automated Static Code Analysis rules. You can add your own rules or use [detekt] rules for analysis as well.
 * This method will configure [SonarQube] at the root of the project.
 * Update the values for [sonar.projectKey], [sonar.organization], [sonar.host.url], [sonar.login] according to your project setup.
 */
internal fun Project.configureSonarqube() {
    val plugin = rootProject.plugins.findPlugin("org.sonarqube")
    if (plugin == null) {
        rootProject.plugins.apply("org.sonarqube")
        rootProject.extensions.getByType<SonarQubeExtension>().run {
            properties {
                property("sonar.projectKey", "nikhil-thakkar_multi-module-dependency-setup")
                property("sonar.organization", "nikhil-thakkar")
                property("sonar.sources", "src/main/java")
                property("sonar.sources.coveragePlugin", "jacoco")
                property("sonar.host.url", "https://sonarcloud.io/")
                property("sonar.exclusions", "**/*.js,**/test/**, buildSrc/*")
                property("sonar.login", "")
            }
        }
    }

    extensions.getByType<SonarQubeExtension>().run {
        properties {
            property(
                "sonar.coverage.jacoco.xmlReportPaths",
                "${buildDir}/jacoco/jacoco.xml"
            )
        }
    }
}

/**
 * Jacoco is a tool to measure code coverage and currently the most widely used.
 * This method would apply [com.hiya.jacoco-android] plugin to each module in the project and their respective build variants.
 * The generated xml reports are available under [build/jacoco/jacoco.xml] for each module.
 * These xml reports are sent to [SonarQube] to be displayed under Coverage section on Dashboard.
 * @see <a href="https://sonarcloud.io/dashboard?id=nikhil-thakkar_multi-module-dependency-setup">Example</a>
 */
internal fun Project.configureJacoco() {
    plugins.apply("com.hiya.jacoco-android")

    extensions.getByType<JacocoPluginExtension>().run {
        toolVersion = "0.8.5"
    }

    tasks.withType<Test>().run {
        all {
            configure<JacocoTaskExtension>() {
                isIncludeNoLocationClasses = true
            }
        }
    }

    //Exclude androidx databinding files
    extensions.getByType<JacocoAndroidUnitTestReportExtension>().run {
        excludes = excludes + listOf(
            "androidx/databinding/**/*.class",
            "**/androidx/databinding/*Binding.class",
            "**/**Bind**/**"
        )
    }

    /*
     * The [com.hiya.jacoco-android] plugin doesn't add the code coverage execution data for Instrumentation tests which are generated by running
     * [connectedDebugAndroidTest] or [connectedAndroidTest]
     * Issue open https://github.com/autonomousapps/jacoco-android-gradle-plugin/issues/1
     * TODO: Remove this block once the issue is fixed
     */
    afterEvaluate {
        tasks.withType<JacocoReport>().run {
            all {
                val tree = fileTree(buildDir)
                tree.include("**/*.ec")
                executionData(tree)
            }
        }
    }
}