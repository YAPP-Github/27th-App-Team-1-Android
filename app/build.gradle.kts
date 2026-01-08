plugins {
    id("ndgl.application")
}

android {
    namespace = Configuration.APPLICATION_ID
}

dependencies {
    // Navigation
    implementation(project(":navigation"))

    // Features
    implementation(project(":feature:home"))
    implementation(project(":feature:auth"))
    implementation(project(":feature:travel"))
    implementation(project(":feature:travel-helper"))

    // Core UI
    implementation(project(":core:ui"))

    debugImplementation(libs.androidx.compose.ui.tooling)
}
