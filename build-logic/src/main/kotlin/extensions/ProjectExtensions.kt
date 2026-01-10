package extensions

import Configuration
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import util.applicationExtension
import util.libraryExtension
import util.libs

internal fun Project.configureAndroidApplication() {
    applicationExtension.apply {
        compileSdk = Configuration.COMPILE_SDK

        defaultConfig {
            minSdk = Configuration.MIN_SDK
        }

        buildTypes {
            release {
                isMinifyEnabled = true
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }

        compileOptions {
            sourceCompatibility = Configuration.JAVA_VERSION
            targetCompatibility = Configuration.JAVA_VERSION
        }
    }

    extensions.configure<KotlinAndroidProjectExtension> {
        compilerOptions {
            jvmTarget.set(JvmTarget.fromTarget(Configuration.JVM_TARGET))
        }
    }
}

internal fun Project.configureAndroidLibrary() {
    libraryExtension.apply {
        compileSdk = Configuration.COMPILE_SDK

        defaultConfig {
            minSdk = Configuration.MIN_SDK
        }

        buildTypes {
            release {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }

        compileOptions {
            sourceCompatibility = Configuration.JAVA_VERSION
            targetCompatibility = Configuration.JAVA_VERSION
        }
    }

    extensions.configure<KotlinAndroidProjectExtension> {
        compilerOptions {
            jvmTarget.set(JvmTarget.fromTarget(Configuration.JVM_TARGET))
        }
    }
    
    dependencies {
        "implementation"(libs.findLibrary("androidx-core-ktx").get())
    }
}

internal fun Project.configureKotlinJvm() {
    extensions.configure<JavaPluginExtension> {
        sourceCompatibility = Configuration.JAVA_VERSION
        targetCompatibility = Configuration.JAVA_VERSION
    }

    extensions.configure<KotlinJvmProjectExtension> {
        compilerOptions {
            jvmTarget.set(JvmTarget.fromTarget(Configuration.JVM_TARGET))
        }
    }
}
