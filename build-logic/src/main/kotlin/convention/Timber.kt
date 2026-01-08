package convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import util.libs

internal fun Project.configureTimber() {
    dependencies {
        "implementation"(libs.findLibrary("timber").get())
    }
}
