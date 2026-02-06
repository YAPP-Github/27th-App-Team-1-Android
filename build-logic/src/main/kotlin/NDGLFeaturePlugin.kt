import convention.configureCoroutineAndroid
import convention.configureHiltAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import util.libs

class NDGLFeaturePlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            apply("ndgl.android.library")
        }

        configureHiltAndroid()
        configureCoroutineAndroid()

        dependencies {
            "implementation"(project(":navigation"))
            "implementation"(project(":core:base"))
            "implementation"(project(":core:ui"))
            "implementation"(project(":core:util"))

            "implementation"(libs.findLibrary("hilt-navigation-compose").get())
            "implementation"(libs.findLibrary("navigation-compose").get())
            "implementation"(libs.findLibrary("lifecycle-viewmodel-compose").get())
            "implementation"(libs.findLibrary("lifecycle-runtime-compose").get())
            "implementation"(libs.findLibrary("androidx-navigation3-runtime").get())
            "implementation"(libs.findLibrary("coil-compose").get())
            "implementation"(libs.findLibrary("coil-network-okhttp").get())
        }
    }
}
