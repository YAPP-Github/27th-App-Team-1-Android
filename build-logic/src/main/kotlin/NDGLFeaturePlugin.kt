import convention.configureComposeAndroid
import convention.configureCoroutineAndroid
import convention.configureFirebase
import convention.configureHiltAndroid
import extensions.configureAndroidLibrary
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import util.libs

class NDGLFeaturePlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            apply("com.android.library")
            apply("org.jetbrains.kotlin.android")
        }

        configureAndroidLibrary()
        configureHiltAndroid()
        configureFirebase()
        configureComposeAndroid()
        configureCoroutineAndroid()


        dependencies {
            "implementation"(project(":navigation"))
            "implementation"(project(":core:ui"))
            "implementation"(project(":core:util"))

            "implementation"(libs.findLibrary("hilt-navigation-compose").get())
            "implementation"(libs.findLibrary("navigation-compose").get())
            "implementation"(libs.findLibrary("lifecycle-viewmodel-compose").get())
            "implementation"(libs.findLibrary("lifecycle-runtime-compose").get())
            "implementation"(libs.findLibrary("kotlinx-immutable").get())
        }
    }
}
