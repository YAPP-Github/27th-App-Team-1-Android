
import convention.configureComposeAndroid
import convention.configureFirebase
import extensions.configureAndroidLibrary
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import util.libs

class NDGLAndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            apply("com.android.library")
            apply("org.jetbrains.kotlin.android")
        }

        configureAndroidLibrary()
        configureFirebase()
        configureComposeAndroid()


        dependencies {
            "implementation"(libs.findLibrary("lifecycle-runtime-compose").get())
            "implementation"(libs.findLibrary("kotlinx-immutable").get())
        }
    }
}
