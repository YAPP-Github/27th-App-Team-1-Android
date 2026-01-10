plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
}

// Use Kotlin BOM with enforcedPlatform to strictly enforce Kotlin stdlib versions
subprojects {
    afterEvaluate {
        configurations.findByName("implementation")?.let {
            dependencies {
                "implementation"(enforcedPlatform("org.jetbrains.kotlin:kotlin-bom:${rootProject.libs.versions.kotlin.get()}"))
            }
        }
    }
}
