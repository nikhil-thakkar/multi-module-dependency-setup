import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/***
 * This plugin is instantiated every time when you apply the this plugin to build.gradle in feature module
 * <code>apply plugin: 'dev.nikhi1.plugin.android'</code>
 */
class AndroidModulePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        if (project.hasProperty("android")) {
            project.configureAndroidBlock()
            project.configureCommonDependencies()
        }
    }
}

internal fun Project.configureAndroidBlock() = extensions.getByType<BaseExtension>().run {

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

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

internal fun Project.configureCommonDependencies() {
    //find the app module
    //replace this with your base module if using otherwise
    val app = findProject(":app")
    extensions.getByType<BaseExtension>().run {
        dependencies {
            if (!(name == "app" || name == "core")) {
                // Since the feature modules need to depend on the :app or the base module
                //Don't add the app to itself or to core
                if (app != null) {
                    add("implementation", app)
                }
            }
            add("testImplementation", Libs.junit)
            add("testImplementation", Libs.mockk)
        }
    }
}