import convention.configureComposeAndroid
import convention.configureHiltAndroid
import convention.configureKotlinAndroid
import convention.configureTimber
import extensions.configureAndroidApplication
import org.gradle.api.Plugin
import org.gradle.api.Project

class NDGLApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            apply("com.android.application")
        }

        configureKotlinAndroid()
        configureAndroidApplication()
        configureHiltAndroid()
        configureTimber()
        configureComposeAndroid()
    }
}
