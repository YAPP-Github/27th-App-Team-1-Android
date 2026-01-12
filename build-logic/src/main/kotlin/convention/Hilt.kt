package convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import util.libs

internal fun Project.configureHiltAndroid() {
    with(pluginManager) {
        apply("dagger.hilt.android.plugin")
        apply("com.google.devtools.ksp")
    }

    dependencies {
        "implementation"(libs.findLibrary("hilt-android").get())
        "ksp"(libs.findLibrary("hilt-compiler").get())
    }
}

internal fun Project.configureHiltKotlin() {
    with(pluginManager) {
        apply("com.google.devtools.ksp")
    }

    dependencies {
        "implementation"(libs.findLibrary("hilt.core").get())
        "ksp"(libs.findLibrary("hilt.compiler").get())
    }
}
