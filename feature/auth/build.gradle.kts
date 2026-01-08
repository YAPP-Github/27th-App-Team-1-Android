plugins {
    id("ndgl.feature")
}

android {
    namespace = "com.yapp.ndgl.feature.auth"
}

dependencies {
    implementation(project(":navigation"))
    implementation(project(":core:ui"))
    implementation(project(":core:util"))
    implementation(project(":data:auth"))
    implementation(libs.hilt.navigation.compose)
}
