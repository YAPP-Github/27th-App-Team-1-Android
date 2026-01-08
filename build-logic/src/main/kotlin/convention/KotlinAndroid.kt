package convention

import Configuration
import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import util.androidExtension
import util.libs

internal fun Project.configureKotlinAndroid() {
    pluginManager.apply("kotlin-android")

    androidExtension.apply {
        compileSdk = Configuration.COMPILE_SDK
        defaultConfig {
            minSdk = Configuration.MIN_SDK
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
        compileOptions {
            sourceCompatibility = Configuration.JAVA_VERSION
            targetCompatibility = Configuration.JAVA_VERSION
            isCoreLibraryDesugaringEnabled = true
        }
    }

    extensions.getByType<BaseExtension>().apply {
        defaultConfig {
            targetSdk = Configuration.TARGET_SDK
            versionCode = Configuration.VERSION_CODE
            versionName = Configuration.VERSION_NAME
        }
    }

    configureKotlin()

    dependencies {
        "coreLibraryDesugaring"(libs.findLibrary("android.desugarJdkLibs").get())
    }
}

internal fun Project.configureKotlin() {
    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
            val warningsAsErrors: String? by project
            allWarningsAsErrors.set(warningsAsErrors.toBoolean())
            freeCompilerArgs.set(
                freeCompilerArgs.get() + listOf(
                    "-opt-in=kotlin.RequiresOptIn",
                )
            )
        }
    }
}
