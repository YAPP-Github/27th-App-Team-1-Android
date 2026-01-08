package convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import util.libs

internal fun Project.configureCoroutineAndroid() {
    configureCoroutineKotlin()
    dependencies {
        "implementation"(libs.findLibrary("kotlinx-coroutines-android").get())
    }
}

internal fun Project.configureCoroutineKotlin() {
    dependencies {
        "implementation"(libs.findLibrary("kotlinx-coroutines-core").get())
    }
}
