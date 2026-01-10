package convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import util.androidExtension
import util.libs

internal fun Project.configureComposeAndroid() {
    with(plugins) {
        apply("org.jetbrains.kotlin.plugin.compose")
    }

    androidExtension.apply {
        buildFeatures {
            compose = true
        }
    }

    dependencies {
        "implementation"(platform(libs.findLibrary("androidx-compose-bom").get()))
        "implementation"(libs.findLibrary("androidx-activity-compose").get())
        "implementation"(libs.findLibrary("androidx-compose-material3").get())
        "implementation"(libs.findLibrary("androidx-compose-ui").get())
        "implementation"(libs.findLibrary("androidx-compose-ui-tooling-preview").get())
        "debugImplementation"(libs.findLibrary("androidx-compose-ui-tooling").get())
    }
}
