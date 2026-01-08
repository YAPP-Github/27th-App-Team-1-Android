plugins {
    id("ndgl.feature")
}

android {
    namespace = "com.yapp.ndgl.feature.home"
}

dependencies {
    implementation(project(":navigation"))
    implementation(project(":core:ui"))
    implementation(project(":core:util"))
    implementation(libs.hilt.navigation.compose)
}
