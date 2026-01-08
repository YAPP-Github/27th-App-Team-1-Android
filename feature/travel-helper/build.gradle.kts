plugins {
    id("ndgl.feature")
}

android {
    namespace = "com.yapp.ndgl.feature.travelHelper"
}

dependencies {
    implementation(project(":navigation"))
    implementation(project(":core:ui"))
    implementation(project(":core:util"))
    implementation(project(":data:travel"))
    implementation(libs.hilt.navigation.compose)
}
