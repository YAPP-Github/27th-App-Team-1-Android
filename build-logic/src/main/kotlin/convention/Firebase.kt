package convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import util.libs

internal fun Project.configureFirebase() {
    dependencies {
        "implementation"(platform(libs.findLibrary("firebase-bom").get()))
        "implementation"(libs.findLibrary("firebase-analytics").get())
        "implementation"(libs.findLibrary("firebase-crashlytics").get())
    }
}
