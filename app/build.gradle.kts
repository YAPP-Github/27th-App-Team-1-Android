import java.util.Properties

plugins {
    id("ndgl.application")
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
}

android {
    namespace = Configuration.APPLICATION_ID

    defaultConfig {
        val localProperties = Properties().apply {
            load(rootProject.file("local.properties").bufferedReader())
        }

        manifestPlaceholders["MAPS_API_KEY"] = localProperties.getProperty("MAPS_API_KEY", "")
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = true
        }
    }
}

dependencies {
    implementation(project(":navigation"))

    implementation(project(":feature:home"))
    implementation(project(":feature:auth"))
    implementation(project(":feature:travel"))
    implementation(project(":feature:travel-helper"))

    implementation(project(":core:ui"))

    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
}
