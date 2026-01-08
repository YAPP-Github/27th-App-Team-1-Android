
import extensions.configureKotlinJvm
import org.gradle.api.Plugin
import org.gradle.api.Project

class NDGLKotlinLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            apply("org.jetbrains.kotlin.jvm")
        }
        configureKotlinJvm()
    }
}

